package internal

import bindings.gobject.Object
import bindings.gobject.typeName
import internal.objectproperties.ObjectClassProperties
import kotlinx.cinterop.*
import native.gobject.*
import usertypes.ObjectClass
import usertypes.ObjectClassInitFunc

internal object TypeRegistry {

    private val typeMap: MutableMap<String, DynamicTypeInfo<*>> = mutableMapOf()
    private val gTypeMap: MutableMap<GType, DynamicTypeInfo<*>> = mutableMapOf()

    fun <T : Object, S : Object> registerType(
        typeName: String,
        superType: KGType<S>,
        classInitFunc: ObjectClassInitFunc
    ): DynamicTypeInfo<T> {
        @Suppress("UNCHECKED_CAST")
        return typeMap.getOrPut(typeName) {
            val registrationResult = buildDynamicTypeInfo(InternalTypeInfo(typeName, superType, classInitFunc))
            val info = DynamicTypeInfo<T>(
                registrationResult.gType, registrationResult.classSize, registrationResult.instanceSize,
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
    val superType: KGType<*>,
    val classInitFunc: ObjectClassInitFunc,
) {
    override fun toString() = "Type: $typeName :: (superType=${superType.gType.typeName})"

    fun getClassPropertiesPointerFromClassInstancePointer(g_class: gpointer): CPointer<CustomObjectClassProperties> {
        val rawPropertiesPointer = g_class.rawValue.plus(this.superType.classSize)
        return interpretCPointer<CustomObjectClassProperties>(rawPropertiesPointer) as CPointer<CustomObjectClassProperties>
    }

}

internal data class TypeRegistrationResult(
    val gType: GType,
    val classSize: Long,
    val instanceSize: Long,
)

private fun buildDynamicTypeInfo(info: InternalTypeInfo): TypeRegistrationResult = memScoped {
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
        info.superType.gType, info.typeName, typeInfoStruct.ptr, 0 // TODO support type flags
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
        // init the ObjectClassProperties
        val objectClassProperties = StableRef.create(ObjectClassProperties(g_class.reinterpret())) // TODO dispose

        val cl = g_class as CPointer<CustomObjectClass>

        cl.pointed.parent_class.set_property = staticKGObjectSetPropertyFunc
        cl.pointed.parent_class.get_property = staticKGObjectGetPropertyFunc
        cl.pointed.parent_class.dispose = staticKGObjectInstanceDispose.reinterpret()

        val propertiesPointer = internalTypeInfo.getClassPropertiesPointerFromClassInstancePointer(g_class)
        propertiesPointer.pointed.kg_type_obj = data
        propertiesPointer.pointed.kg_object_class_properties = objectClassProperties.asCPointer()

        // run user-defined classInit
        val classRef = ObjectClass<Object>(g_class, objectClassProperties.get())
        internalTypeInfo.classInitFunc.invoke(classRef)

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

/**
 * INSTANCE SET PROPERTY STATIC C FUNCTION
 */
private val staticKGObjectSetPropertyFunc =
    staticCFunction { gobj: GObject_autoptr?,
                      prop_id: guint,
                      value: CPointer<GValue>?,
                      paramSpec: CPointer<GParamSpec>? ->

        val classGType = gobj!!.pointed.g_type_instance.g_class!!.pointed.g_type
        val typeInfo = TypeRegistry.getTypeInfoForType(classGType)!!

        val instanceProperties = gobj.getCustomObjectPropertiesPointer(typeInfo)
        val instanceRef = instanceProperties.pointed.internal_obj
            ?.asStableRef<InstanceDataHolder>()?.get()
            ?.data

        if (instanceRef != null) {
            val objectClassProperties =
                typeInfo.getObjectClassPropertiesForClassPointer(gobj.pointed.g_type_instance.g_class!!)
            objectClassProperties.setPropertyValue(instanceRef, prop_id, value!!, paramSpec!!)
        }

        Unit
    }

/**
 * INSTANCE GET PROPERTY STATIC C FUNCTION
 */
private val staticKGObjectGetPropertyFunc =
    staticCFunction { gobj: GObject_autoptr?,
                      prop_id: guint,
                      value: CPointer<GValue>?,
                      paramSpec: CPointer<GParamSpec>? ->

        val classGType = gobj!!.pointed.g_type_instance.g_class!!.pointed.g_type
        val typeInfo = TypeRegistry.getTypeInfoForType(classGType)!!

        val instanceProperties = gobj.getCustomObjectPropertiesPointer(typeInfo)
        val instanceRef = instanceProperties.pointed.internal_obj
            ?.asStableRef<InstanceDataHolder>()?.get()
            ?.data

        if (instanceRef != null) {
            val objectClassProperties =
                typeInfo.getObjectClassPropertiesForClassPointer(gobj!!.pointed.g_type_instance.g_class!!)
            objectClassProperties.getPropertyValue(instanceRef, prop_id, value!!, paramSpec!!)
        }

        Unit
    }