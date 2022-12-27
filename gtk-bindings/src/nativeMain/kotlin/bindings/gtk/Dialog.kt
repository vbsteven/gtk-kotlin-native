package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.gboolean
import bindings.gtk.internal.staticStableRefDestroy
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gint
import native.gobject.gpointer
import native.gtk.*

open class Dialog : Window {

    val gtkDialogPointer get() = gtkWindowPointer.asTypedPointer<GtkDialog>()

    constructor() : super(gtk_dialog_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    fun addButton(buttonText: String, responseId: Int) {
        gtk_dialog_add_button(gtkDialogPointer, buttonText, responseId)
    }

    fun addButtons(vararg buttons: Pair<String, Int>) = buttons.forEach {
        this.addButton(it.first, it.second)
    }

    fun addActionWidget(child: Widget, responseId: Int) {
        // TODO this api only supports activatable widgets, can we provide this in the signature?
        gtk_dialog_add_action_widget(gtkDialogPointer, child.gtkWidgetPointer, responseId)
    }

    val contentArea: Box
        get() = gtk_dialog_get_content_area(gtkDialogPointer)!!.asTypedPointer<GtkBox>().asBox()

    val headerBar: HeaderBar
        get() = gtk_dialog_get_header_bar(gtkDialogPointer)!!.asTypedPointer<GtkHeaderBar>().asHeaderBar()

    fun getResponseForWidget(widget: Widget): Int {
        return gtk_dialog_get_response_for_widget(gtkDialogPointer, widget.gtkWidgetPointer)
    }

    fun getWidgetForResponse(responseId: Int): Widget? {
        return gtk_dialog_get_widget_for_response(gtkDialogPointer, responseId)?.asWidget()
    }

    /**
     * Emits the ::response signal with given [responseId]
     */
    fun response(responseId: Int) {
        gtk_dialog_response(gtkDialogPointer, responseId)
    }

    fun setDefaultResponse(responseId: Int) = gtk_dialog_set_default_response(gtkDialogPointer, responseId)

    fun setResponseSensitive(responseId: Int, sensitive: Boolean) =
        gtk_dialog_set_response_sensitive(gtkDialogPointer, responseId, sensitive.gboolean)

    /**
     * Connect a handler to the response signal.
     */
    fun onResponse(func: (Int) -> Unit) {
        g_signal_connect_data(
            gtkWidgetPointer,
            "response",
            staticDialogResponseCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    /**
     * Connect a handler to the close signal. This signal is emitted when the user
     * uses a keybinding to close the dialog.
     */
    fun onClose(func: () -> Unit) {
        g_signal_connect_data(
            gtkWidgetPointer,
            "close",
            staticDialogCloseCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }


    companion object : ObjectCompanion<Dialog>(DialogTypeInfo) {
        fun newDialogWithButtons(
            title: String?,
            parent: Window?,
            flags: GtkDialogFlags,
            vararg buttons: Pair<String, Int>
        ): Dialog {
            return Dialog(
                gtk_dialog_new_with_buttons(
                    title,
                    parent?.gtkWindowPointer,
                    flags,
                    null
                )!!
            ).apply {
                buttons.forEach {
                    this.addButton(it.first, it.second)
                }
            }
        }
    }
}

private val DialogTypeInfo = BuiltinTypeInfo(
    "GtkDialog",
    GTK_TYPE_DIALOG,
    sizeOf<GtkDialogClass>(),
    sizeOf<GtkDialog>(),
    ::Dialog
)

private val staticDialogResponseCallbackFunc: GCallback =
    staticCFunction { _: CPointer<GtkDialog>,
                      response: gint,
                      data: gpointer ->
        data.asStableRef<(Int) -> Unit>().get().invoke(response)
    }.reinterpret()

private val staticDialogCloseCallbackFunc: GCallback =
    staticCFunction { _: CPointer<GtkDialog>,
                      data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
    }.reinterpret()
