package bindings.gobject

import internal.BuiltinTypeInfo
import internal.TypeRegistry
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gobject.*


open class Object(pointer: CPointer<*>) {
    val gPointer: CPointer<*> = pointer

    constructor() : this(typeInfo.newInstancePointer())

    init {
        associateCustomObject()
    }

    /* Property binding */

    fun bindProperty(
        property: String,
        target: Object,
        targetProperty: String,
        flags: GBindingFlags = G_BINDING_DEFAULT
    ): Binding {
        return g_object_bind_property(gPointer, property, target.gPointer, targetProperty, flags)!!.asBinding()
    }

    /* Reference counting */

    /**
     * Decrease the reference count of this object.
     */
    fun unref() {
        g_object_unref(gPointer)
    }

    /**
     * Increase the reference count of this object.
     */
    fun ref() {
        g_object_ref(gPointer)
    }

    /**
     * Increase the reference count of this object, possibly removing the floating reference if object has a floating
     * reference.
     */
    fun refSink() {
        g_object_ref_sink(gPointer)
    }

    /**
     * Check whether this object has a floating reference.
     */
    val isFloating: Boolean get() = g_object_is_floating(gPointer).boolean

    /* Notify */

    /**
     * Increases the freeze count on object.
     *
     * If the freeze count is non-zero, the emission of “notify” signals on object is stopped. The signals are queued
     * until the freeze count is decreased to zero. Duplicate notifications are squashed so that at most one
     * GObject::notify signal is emitted for each property modified while the object is frozen.
     *
     * This is necessary for accessors that modify multiple properties to prevent premature notification while the
     * object is still being modified.
     */
    fun freezeNotify() = g_object_freeze_notify(gPointer.asTypedPointer())

    /**
     * Reverts the effect of a previous call to [freezeNotify].
     * The freeze count is decreased on object and when it reaches zero, queued “notify” signals are emitted.
     *
     * Duplicate notifications for each property are squashed so that at most one GObject::notify signal is emitted
     * for each property, in the reverse order in which they have been queued.
     *
     * It is an error to call this function when the freeze count is zero.
     */
    fun thawNotify() = g_object_thaw_notify(gPointer.asTypedPointer())

    companion object : ObjectCompanion<Object>(ObjectTypeInfo)

    private fun associateCustomObject() {
        // TODO is there a better way to get the type from an object?
        val typeInfo = g_type_name_from_instance(gPointer.reinterpret())
            ?.toKString()
            ?.let { TypeRegistry.getTypeInfoForName(it) }

        typeInfo?.associate(this, this.gPointer)
    }
}

val ObjectTypeInfo = BuiltinTypeInfo<Object>(
    "GObject",
    G_TYPE_OBJECT,
    sizeOf<native.gobject.GObjectClass>(),
    sizeOf<native.gobject.GObject>(),
    ::Object
)
