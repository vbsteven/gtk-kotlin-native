import bindings.adw.Avatar
import bindings.adw.MessageDialog
import bindings.adw.Toast
import bindings.adw.ToastOverlay
import bindings.gtk.ListBox
import native.adwaita.AdwResponseAppearance
import kotlin.test.Test

class AdMessageDialogTest : AdwTestBase() {

    @Test
    fun testMessageDialogs() = runApplicationWindow { window ->
        val toastOverlay = ToastOverlay()
        toastOverlay.vexpand = true
        val listBox = ListBox()

        listBox.addButton("Simple Message Dialog") {
            MessageDialog(window, "Heading", "Body").show()
        }
        listBox.addButton("Widget Dialog") {
            MessageDialog(window, "Heading", "Body").apply {
                extraChild = Avatar(30, null, false)
            }.show()
        }
        listBox.addButton("Responses Dialog") {
            MessageDialog(window, "Heading", "Body").apply {
                addResponse("cancel", "Cancel")
                setResponseAppearance("cancel", AdwResponseAppearance.ADW_RESPONSE_DEFAULT)
                addResponse("discard", "Discard")
                setResponseAppearance("discard", AdwResponseAppearance.ADW_RESPONSE_DESTRUCTIVE)
                addResponse("save", "Save")
                setResponseAppearance("save", AdwResponseAppearance.ADW_RESPONSE_SUGGESTED)

                onResponse { response ->
                    toastOverlay.addToast(Toast("You chose : $response"))
                }

            }.show()
        }

        toastOverlay.child = listBox

        window.setTestContent("Message Dialogs", toastOverlay)
    }
}