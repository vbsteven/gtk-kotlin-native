package internal

import bindings.gobject.Object
import kotlinx.cinterop.*
import native.gobject.*

internal object TypeRegistry {

    private val typeMap: MutableMap<String, DynamicTypeInfo<*>> = mutableMapOf()
    private val gTypeMap: MutableMap<GType, DynamicTypeInfo<*>> = mutableMapOf()

    fun <T : Object, S : Object> registerType(
        typeName: String,
        superType: KGTypeInfo<S>,
    ): DynamicTypeInfo<T> {
        @Suppress("UNCHECKED_CAST")
        return typeMap.getOrPut(typeName) {
            val registrationResult = buildDynamicTypeInfo(InternalTypeInfo(typeName, superType))
            val info = DynamicTypeInfo<T>(
                typeName, registrationResult.gType, registrationResult.classSize, registrationResult.instanceSize,
                superType.classSize,
                superType.instanceSize,
            )
            gTypeMap[info.gType] = info
            info
        } as DynamicTypeInfo<T>
    }

    fun getTypeInfoForType(gType: GType) = gTypeMap[gType]

    fun getTypeInfoForName(name: String) = typeMap[name]
}

/**
 * Intermediate object for passing around to static C type methods
 */
internal data class InternalTypeInfo(
    val typeName: String,
    val superType: KGTypeInfo<*>,
) {
    override fun toString() = "Type: $typeName :: (superType=${superType.name})"

    fun getClassPropertiesPointerFromClassInstancePointer(g_class: gpointer): CPointer<CustomObjectClassProperties> {
        val rawPropertiesPointer = g_class.rawValue.plus(this.superType.classSize)
        return interpretCPointer<CustomObjectClassProperties>(rawPropertiesPointer) as CPointer<CustomObjectClassProperties>
    }

}

internal data class TypeRegistrationResult(
    val gType: GType,
    val classSize: Long,
    val instanceSize: Long
)

private fun buildDynamicTypeInfo(info: InternalTypeInfo): TypeRegistrationResult = memScoped {
    println("TypeRegistry :: Registering $info")

    val stableInternalInfo = StableRef.create(info) // TODO dispose when class gets disposed

    val classSize: Long = info.superType.classSize + sizeOf<CustomObjectClassProperties>()
    val instanceSize: Long = info.superType.instanceSize + sizeOf<CustomObjectProperties>()

    val typeInfoStruct = alloc<GTypeInfo> {
        class_size = classSize.toUShort()
        instance_size = instanceSize.toUShort()
        class_init = staticCustomObjectClassInit
        // TODO class finalize
        instance_init = staticKGObjectInstanceInit
        class_data = stableInternalInfo.asCPointer()
    }

    val gType = g_type_register_static(
        info.superType.gType, info.typeName, typeInfoStruct.ptr, G_TYPE_FLAG_NONE // TODO support type flags
    )

    if (gType.toInt() <= 0) {
        throw Exception("Error registering custom type $info")
    }
    println("TypeRegistry :: registered new gType: $gType")

    return TypeRegistrationResult(gType, classSize, instanceSize)
}

internal class InstanceDataHolder(val data: Any)

/**
 * Pointer arithmetic to get a pointer to the properties holder after the instance parent data
 */
internal fun CPointer<*>.getCustomObjectPropertiesPointer(typeInfo: DynamicTypeInfo<*>): CPointer<CustomObjectProperties> {
    val rawPropertiesPointer = this.rawValue.plus(typeInfo.parentInstanceSize)
    return interpretCPointer<CustomObjectProperties>(rawPropertiesPointer) as CPointer<CustomObjectProperties>
}


/**
 * CLASS INIT STATIC C FUNCTION
 */
private val staticCustomObjectClassInit: GClassInitFunc =
    staticCFunction { g_class: gpointer,
                      data: CPointer<*> /* KGTypeInfo StableRef */ ->
        println("INITIALIZING CustomObject class")

        val internalTypeInfo = data.asStableRef<InternalTypeInfo>().get()

        val cl = g_class as CPointer<CustomObjectClass>
        // TODO install property getter/setter functions
        // TODO enable these again
//        cl.pointed.parent_class.set_property = staticKGObjectSetPropertyFunc
//        cl.pointed.parent_class.get_property = staticKGObjectGetPropertyFunc
        cl.pointed.parent_class.dispose = staticKGObjectInstanceDispose.reinterpret()

        val propertiesPointer = internalTypeInfo.getClassPropertiesPointerFromClassInstancePointer(g_class)
        propertiesPointer.pointed.kg_type_obj = data

        // TODO add class finalize to cleanup data pointer
    }.reinterpret()

/**
 * INSTANCE INIT STATIC C FUNCTION
 */
private val staticKGObjectInstanceInit: GInstanceInitFunc =
    staticCFunction { type_instance: CPointer<CustomObjectClass>,
                      g_class: CPointer<GTypeClass> ->

        val classGType = g_class.pointed.g_type
        val internalTypeInfo = TypeRegistry.getTypeInfoForType(classGType)!!

        val propertiesPointer = type_instance.getCustomObjectPropertiesPointer(internalTypeInfo)
        propertiesPointer.pointed.dispose_has_run = 0
        propertiesPointer.pointed.test_value = 42
    }.reinterpret()

/**
 * INSTANCE DISPOSE STATIC C FUNCTION
 */
private val staticKGObjectInstanceDispose =
    staticCFunction { gobj: GObject_autoptr ->
        val classGType = gobj.pointed.g_type_instance.g_class!!.pointed.g_type
        val typeInfo = TypeRegistry.getTypeInfoForType(classGType)!!

        val instanceProperties = gobj.getCustomObjectPropertiesPointer(typeInfo)
        if (instanceProperties.pointed.dispose_has_run > 0) {
            return@staticCFunction null
        }
        instanceProperties.pointed.dispose_has_run = 1

        // cleanup instance data StableRef if associated
        instanceProperties.pointed.internal_obj
            ?.asStableRef<InstanceDataHolder>()
            ?.dispose()

        // chain up to parent class dispose
        val instancePointer = gobj as CPointer<CustomObject>
        val parentClassPointer =
            g_type_class_peek_parent(instancePointer.pointed.parent_instance.g_type_instance!!.g_class)!! as CPointer<GObjectClass>
        parentClassPointer.pointed.dispose!!.invoke(gobj)
        null as CPointer<*>?
    }
