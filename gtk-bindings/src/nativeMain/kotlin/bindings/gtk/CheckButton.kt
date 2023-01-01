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

open class CheckButton : Widget {
    val gtkCheckButtonPointer get() = gtkWidgetPointer.asTypedPointer<GtkCheckButton>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(label: String? = null, withMnemonic: Boolean = false) : this(newPointer(label, withMnemonic))

    var active: Boolean
        get() = gtk_check_button_get_active(gtkCheckButtonPointer).boolean
        set(value) = gtk_check_button_set_active(gtkCheckButtonPointer, value.gboolean)

    var child: Widget?
        get() = gtk_check_button_get_child(gtkCheckButtonPointer)?.asWidget()
        set(value) = gtk_check_button_set_child(gtkCheckButtonPointer, value?.gtkWidgetPointer)

    var inconsistent: Boolean
        get() = gtk_check_button_get_inconsistent(gtkCheckButtonPointer).boolean
        set(value) = gtk_check_button_set_inconsistent(gtkCheckButtonPointer, value.gboolean)

    var label: String?
        get() = gtk_check_button_get_label(gtkCheckButtonPointer)?.toKString()
        set(value) = gtk_check_button_set_label(gtkCheckButtonPointer, value)

    var useUnderline: Boolean
        get() = gtk_check_button_get_use_underline(gtkCheckButtonPointer).boolean
        set(value) = gtk_check_button_set_use_underline(gtkCheckButtonPointer, value.gboolean)

    fun onToggled(func: (CheckButton) -> Unit) {
        val handler = { func(this) }
        g_signal_connect_data(
            gtkCheckButtonPointer,
            "toggled",
            staticCheckButtonSignalFunc,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_CHECK_BUTTON,
            sizeOf<GtkCheckButtonClass>(),
            sizeOf<GtkCheckButton>(),
            ::CheckButton
        )

        private fun newPointer(label: String?, withMnemonic: Boolean) =
            if (withMnemonic) newPointerWithMnemonic(label)
            else newPointerWithLabel(label)

        private fun newPointerWithLabel(label: String?) = gtk_check_button_new_with_label(label)!!
        private fun newPointerWithMnemonic(label: String?) = gtk_check_button_new_with_mnemonic(label)!!
    }
}

private val staticCheckButtonSignalFunc: GCallback =
    staticCFunction { _: gpointer, data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
        Unit
    }.reinterpret()
