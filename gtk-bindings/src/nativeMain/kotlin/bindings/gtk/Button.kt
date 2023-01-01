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

open class Button : Widget, Actionable {

    val gtkButtonPointer get() = gPointer.asTypedPointer<GtkButton>()
    override val gtkActionablePointer get() = gPointer.asTypedPointer<GtkActionable>()

    constructor() : this(gtk_button_new()!!)
    constructor(label: String) : this(gtk_button_new_with_label(label)!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var label: String?
        get() = gtk_button_get_label(gtkButtonPointer)?.toKString()
        set(value) = gtk_button_set_label(gtkButtonPointer, value)

    var iconName: String?
        get() = gtk_button_get_icon_name(gtkButtonPointer)?.toKString()
        set(value) = gtk_button_set_icon_name(gtkButtonPointer, value)

    var child: Widget?
        get() = gtk_button_get_child(gtkButtonPointer)?.asWidget()
        set(value) = gtk_button_set_child(gtkButtonPointer, value?.gtkWidgetPointer)

    var hasFrame: Boolean
        get() = gtk_button_get_has_frame(gtkButtonPointer).boolean
        set(value) = gtk_button_set_has_frame(gtkButtonPointer, value.gboolean)

    var useUnderline: Boolean
        get() = gtk_button_get_use_underline(gtkButtonPointer).boolean
        set(value) = gtk_button_set_use_underline(gtkButtonPointer, value.gboolean)


    /**
     * Connect [func] as a handler for the "clicked" signal.
     */
    fun onClicked(func: (Button) -> Unit) {
        g_signal_connect_data(
            gPointer,
            "clicked", staticButtonClickedFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_BUTTON,
            sizeOf<GtkButtonClass>(),
            sizeOf<GtkButton>(),
            ::Button
        )

        fun newFromIconName(iconName: String) = Button(gtk_button_new_from_icon_name(iconName)!!)
        fun newWithLabel(label: String) = Button(label)
        fun newWithMnemonic(label: String) = Button(gtk_button_new_with_mnemonic(label)!!)
    }
}


private val staticButtonClickedFunc: GCallback =
    staticCFunction { buttonPointer: CPointer<GtkButton>?,
                      data: gpointer? ->
        val button = buttonPointer!!.asButton()
        val func = data!!.asStableRef<(Button) -> Unit>().get()
        func.invoke(button)
    }.reinterpret()

