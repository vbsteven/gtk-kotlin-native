package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import native.gtk.GTK_DIALOG_DESTROY_WITH_PARENT
import native.gtk.GTK_DIALOG_USE_HEADER_BAR
import native.gtk.GTK_RESPONSE_ACCEPT
import native.gtk.GTK_RESPONSE_REJECT
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
        }

        dialog.onClose {
            println("Dialog closed")
        }
        dialog.isModal = true
        dialog.present()
    }

}