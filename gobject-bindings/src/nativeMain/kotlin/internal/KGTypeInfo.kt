package internal

import bindings.gobject.Object
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.pointed
import native.gobject.GType
import native.gobject.g_object_new

/**
 * Info about a GObject type.
 */
abstract class KGTypeInfo<T : Object> {
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
) : KGTypeInfo<T>() {
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
) : KGTypeInfo<T>() {
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
        if(instanceProperties.pointed.internal_obj != null) {
            throw Error("Object already associated")
        }
        val dataHolder = InstanceDataHolder(obj)
        instanceProperties.pointed.internal_obj = StableRef.create(dataHolder).asCPointer()
    }
}
