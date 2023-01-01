package bindings.gobject

import internal.KGType
import kotlinx.cinterop.CPointer
import native.gobject.g_object_new
import usertypes.ObjectClass
import usertypes.registerTypeClass

/**
 * Companion base class used for registering custom [Object] types in the GObject type system.
 *
 * @param T class for which this companion will register a new type.
 */
abstract class ObjectCompanion<T : Object>() {
    /**
     * Name of the Object type.
     */
    abstract val typeName: String

    /**
     * Parent type info.
     *
     * This should be the Type object of the class that your own class extends.
     */
    abstract val parentType: KGType<*>

    /**
     * Class initializer.
     *
     * This method will be invoked once during the type registration process.
     *
     * @param klass The ObjectClass instance that can be used for installing
     *              properties and signals on your custom type.
     * @see ObjectClass
     */
    open fun classInit(klass: ObjectClass<T>) {}

    /**
     * Instantiate a new instance pointer of the associated Object subclass.
     *
     * This method should be used in constructors of the associated Object subclass
     * and the resulting pointer should be passed on to the constructor of the parent class.
     *
     * @return a pointer to the instance
     */
    fun newInstancePointer(): CPointer<*> = g_object_new(Type.gType, null)!!

    /**
     * Convert a [CPointer] back to an instance of the associated Object subclass.
     *
     * This method should be used instead of simply passing the pointer to the subclass constructor.
     *
     * @return the instance
     * @exception Error when the pointer is not a valid pointer to an instance.
     */
    fun instanceFromPointer(pointer: CPointer<*>): T {
        return Type.instanceFromPointer(pointer)
    }

    /**
     * Type property of the associated Object class.
     *
     * Accessing this property lazily triggers the type registration process.
     */
    val Type by lazy {
        registerType()
    }

    /* Type registration */

    /**
     * Actual type registration, this should only be called once.
     */
    private fun registerType(): KGType<T> {
        val info = registerTypeClass<T>(
            typeName,
            parentType
        ) { objectClass: ObjectClass<*> ->
            // delegate to user init
            classInit(objectClass as ObjectClass<T>)
        }

        return info
    }

}