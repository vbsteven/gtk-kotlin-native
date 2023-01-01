package bindings.gobject

import internal.BuiltinTypeInfo
import internal.TypeRegistry
import internal.staticStableRefDestroy
import kotlinx.cinterop.*
import native.gobject.*


open class Object(pointer: CPointer<*>) {
    val gPointer: CPointer<*> = pointer

    /**
     * Get the GType from the current instance
     */
    val gType: GType by lazy { gPointer.asTypedPointer<GObject>().pointed!!.g_type_instance.g_class!!.pointed.g_type }

    constructor() : this(Type.newInstancePointer())

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

    /* Signals */

    /**
     * Emit a signal by name without arguments.
     */
    fun emitSignal(signalName: String) = g_signal_emit_by_name(gPointer, signalName)

    /**
     * Connect [handler] to signal by name.
     *
     * @param signalName name of the signal to connect to
     * @param flags signal flags passed to g_signal_connect
     * @param handler handler to be invoked on signal emission
     * @return handler id
     */
    fun connectSignal(signalName: String, flags: GSignalFlags = 0.toUInt(), handler: () -> Unit): ULong =
        g_signal_connect_data(
            gPointer, signalName,
            staticNoArgSignalHandler,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            flags
        )

    private fun associateCustomObject() {
        TypeRegistry.getTypeInfoForType(gType)?.associate(this, this.gPointer)
    }

    /* properties */

    fun setProperty(name: String, value: String?) = memScoped {
        val gValue = alloc<GValue>()
        g_value_init(gValue.ptr, G_TYPE_STRING)
        g_value_set_string(gValue.ptr, value)
        g_object_set_property(gPointer.asTypedPointer(), name, gValue.ptr)
    }

    fun setProperty(name: String, value: Int) = memScoped {
        val gValue = alloc<GValue>()
        g_value_init(gValue.ptr, G_TYPE_INT)
        g_value_set_int(gValue.ptr, value)
        g_object_set_property(gPointer.asTypedPointer(), name, gValue.ptr)
    }

    fun getStringProperty(name: String): String? = memScoped {
        val gValue = alloc<GValue>()
        g_value_init(gValue.ptr, G_TYPE_STRING)
        g_object_get_property(gPointer.asTypedPointer(), name, gValue.ptr)
        return g_value_get_string(gValue.ptr)?.toKString()
    }

    fun getIntProperty(name: String): Int = memScoped {
        val gValue = alloc<GValue>()
        g_value_init(gValue.ptr, G_TYPE_INT)
        g_object_get_property(gPointer.asTypedPointer(), name, gValue.ptr)
        return g_value_get_int(gValue.ptr)
    }

    override fun toString(): String {
        val objectTypeName = g_type_name_from_instance(gPointer.asTypedPointer())?.toKString()
        return "Object :: $objectTypeName :: ${super.toString()})"
    }




    companion object {
        val Type = BuiltinTypeInfo(
            "GObject",
            G_TYPE_OBJECT,
            sizeOf<GObjectClass>(),
            sizeOf<GObject>(),
            ::Object
        )
    }
}


private val staticNoArgSignalHandler: GCallback =
    staticCFunction { objectPointer: gpointer,
                      data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
        Unit
    }.reinterpret()
