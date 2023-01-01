package internal

import bindings.gobject.Object
import internal.objectproperties.ObjectClassProperties
import kotlinx.cinterop.*
import native.gobject.*

/**
 * Info about a GObject type.
 */
abstract class KGType<T : Object> {
    /**
     * Name of this type.
     */
    abstract val name: String

    /**
     * GType identifier.
     */
    abstract val gType: GType

    /**
     * Size of the class struct.
     */
    abstract val classSize: Long

    /**
     * Size of the instance struct.
     */
    abstract val instanceSize: Long

    /**
     * Create a new instance of this class using g_object_new.
     */
    fun newInstancePointer(): CPointer<*> {
        // TODO add some error handler to make sure we can instantiate
        return g_object_new(gType, null)!!
    }

    /**
     * Convert the given pointer to an instance of the associated Kotlin class.
     * @return the class instance or null.
     */
    abstract fun instanceFromPointerOrNull(pointer: CPointer<*>): T?

    /**
     * Convert the given pointer to an instance of the associated Kotlin class.
     * @throws when the the conversion failed.
     * @return the class instance.
     */
    fun instanceFromPointer(pointer: CPointer<*>): T = instanceFromPointerOrNull(pointer)!!

    override fun toString() = "TypeInfo [type:$name, gType:$gType]"
}

/**
 * Type info for builtin/wrapped types.
 *
 * This should not be used for implementing custom types. Use [DynamicTypeInfo] instead.
 */
class BuiltinTypeInfo<T : Object>(
    override val name: String,
    override val gType: GType,
    override val classSize: Long,
    override val instanceSize: Long,
    val convertPointerFunc: (CPointer<*>) -> T,
) : KGType<T>() {
    override fun instanceFromPointerOrNull(pointer: CPointer<*>): T? {
        return convertPointerFunc(pointer)
    }
}


/**
 * Type info for dynamically registered types
 */
internal class DynamicTypeInfo<T : Object>(
    override val name: String,
    override val gType: GType,
    override val classSize: Long,
    override val instanceSize: Long,
    val parentClassSize: Long,
    val parentInstanceSize: Long,
) : KGType<T>() {
    override fun instanceFromPointerOrNull(pointer: CPointer<*>): T? {
        return getInstanceDataPointer(pointer)?.asStableRef<InstanceDataHolder>()?.get()?.data as T?
    }

    private fun getInstanceDataPointer(pointer: CPointer<*>): CPointer<*>? {
        val instanceProperties = pointer.getCustomObjectPropertiesPointer(this)
        if (instanceProperties.pointed.internal_obj == null) {
            // TODO warn that object was not associated?
        }
        return instanceProperties.pointed.internal_obj
    }

    internal fun associate(obj: Object, pointer: CPointer<*>) {
        val instanceProperties = pointer.getCustomObjectPropertiesPointer(this)
        if (instanceProperties.pointed.internal_obj == null) {
            val dataHolder = InstanceDataHolder(obj)
            instanceProperties.pointed.internal_obj = StableRef.create(dataHolder).asCPointer()
        }
    }

    fun getObjectClassPropertiesForClassPointer(pointer: CPointer<GTypeClass>): ObjectClassProperties {
        val raw = pointer.rawValue.plus(parentClassSize)
        val interpreted = interpretCPointer<CustomObjectClassProperties>(raw)!!
        return interpreted.pointed.kg_object_class_properties!!.asStableRef<ObjectClassProperties>().get()
    }
}
