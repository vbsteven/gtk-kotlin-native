package bindings.gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class MessageDialog : Dialog {

    val gtkMessageDialogPointer get() = gtkDialogPointer.asTypedPointer<GtkMessageDialog>()

    constructor(
        parent: Window?,
        flags: GtkDialogFlags,
        type: GtkMessageType,
        buttonType: GtkButtonsType,
        message: String,
    ) : super(
        gtk_message_dialog_new(
            parent?.gtkWindowPointer,
            flags, type, buttonType, message
        )!!
    )

    constructor(pointer: CPointer<*>) : super(pointer)

    val messageArea: Box
        get() = gtk_message_dialog_get_message_area(gtkMessageDialogPointer)!!
            .asTypedPointer<GtkBox>()
            .asBox()

    fun setMarkup(markup: String) = gtk_message_dialog_set_markup(gtkMessageDialogPointer, markup)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_MESSAGE_DIALOG, ::MessageDialog)
    }
}
