package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import native.gtk.*
import kotlin.test.Test

class GtkDialogTest : GtkTestBase() {

    @Test
    fun testDialogWithButtons() = runTestApplicationWindow { window ->

        val dialog = Dialog.newDialogWithButtons(
            "My Dialog",
            window,
            GTK_DIALOG_DESTROY_WITH_PARENT.and(GTK_DIALOG_USE_HEADER_BAR),
            "OK" to GTK_RESPONSE_ACCEPT,
            "Cancel" to GTK_RESPONSE_REJECT
        )

        dialog.contentArea.append(Label("Are you sure?"))

        dialog.onResponse {
            println("Response received: $it")
            dialog.close()
        }

        dialog.onClose {
            println("Dialog closed")
        }
        dialog.isModal = true
        dialog.show()
    }

    @Test
    fun testMessageDialog() = runTestApplicationWindow { window ->
        val dialog =
            MessageDialog(
                window,
                GTK_DIALOG_MODAL.and(GTK_DIALOG_USE_HEADER_BAR),
                GtkMessageType.GTK_MESSAGE_WARNING,
                GtkButtonsType.GTK_BUTTONS_OK,
                "Are you sure?"
            )

        dialog.addButton("Cancel", GTK_RESPONSE_CANCEL)

        dialog.onResponse {
            println("Response received: $it")
            dialog.close()
        }
        dialog.onClose {
            println("Dialog closed")
        }

        dialog.show()
    }

}