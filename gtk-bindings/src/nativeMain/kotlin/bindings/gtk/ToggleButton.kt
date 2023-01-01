package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.internal.staticStableRefDestroy
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

open class ToggleButton : Button {
    val gtkToggleButtonPointer get() = gtkWidgetPointer.asTypedPointer<GtkToggleButton>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_toggle_button_new()!!)
    constructor(label: String) : this(gtk_toggle_button_new_with_label(label)!!)

    var active: Boolean
        get() = gtk_toggle_button_get_active(gtkToggleButtonPointer).boolean
        set(value) = gtk_toggle_button_set_active(gtkToggleButtonPointer, value.gboolean)

    fun setGroup(group: ToggleButton) =
        gtk_toggle_button_set_group(gtkToggleButtonPointer, group.gtkToggleButtonPointer)

    /**
     * Emits the toggled signal on this ToggleButton.
     */
    fun toggled() = gtk_toggle_button_toggled(gtkToggleButtonPointer)

    fun onToggled(func: (ToggleButton) -> Unit) {
        val handler = { func(this) }
        g_signal_connect_data(
            gtkToggleButtonPointer,
            "toggled",
            staticToggleButtonSignalFunc,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_TOGGLE_BUTTON,
            sizeOf<GtkToggleButtonClass>(),
            sizeOf<GtkToggleButton>(),
            ::ToggleButton
        )
    }

}

private val staticToggleButtonSignalFunc: GCallback =
    staticCFunction { _: gpointer, data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
        Unit
    }.reinterpret()