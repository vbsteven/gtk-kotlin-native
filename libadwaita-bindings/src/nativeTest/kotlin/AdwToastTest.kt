import bindings.adw.Avatar
import bindings.adw.HeaderBar
import bindings.adw.Toast
import bindings.adw.ToastOverlay
import bindings.gio.SimpleAction
import bindings.gtk.*
import native.gtk.GtkOrientation
import kotlin.test.Test

class AdwToastTest : AdwTestBase() {

    @Test
    fun testToast() = runApplicationWindow { window ->
        val overlay = ToastOverlay()
        val listBox = ListBox()
        overlay.child = listBox
        overlay.vexpand = true

        listBox.appendAll(
            overlay.toastButton("Simple Toast") {
                Toast("Simple Toast")
            },
            overlay.toastButton("Toast with button") {
                Toast("Toast with button label").apply { buttonLabel = "Click me" }
            },
            overlay.toastButton("Toast with custom title") {
                Toast("").apply {
                    customTitle = Avatar(30, null, false)
                }
            },
            overlay.toastButton("Toast with complex custom title") {
                Toast("").apply {
                    val titleBox = Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 20)
                    titleBox.append(Avatar(30, null, false))
                    titleBox.append(Label("A name"))
                    customTitle = titleBox
                }
            },
            overlay.toastButton("Toast with dismiss handler") {
                Toast("Dismiss me").apply {
                    onDismissed { println("!!!!!! Toast was dismissed") }
                }
            },
            overlay.toastButton("Toast with button click handler") {
                Toast("Click the button").apply {
                    buttonLabel = "Click me!"
                    onButtonClicked { println("!!!! The button was clicked") }
                }
            },
            overlay.toastButton("Toast triggers window action") {
                Toast("Send window action").apply {
                    buttonLabel = "Close the window"
                    detailedActionName = "win.close"
                }
            }
        )

        // add the win.close action for the window action toast
        val closeAction = SimpleAction("close")
        closeAction.onActivate {
            println("!!! Close action triggered")
            window.close()
        }
        window.addAction(closeAction)

        // add a headerbar
        val header = HeaderBar().apply {
            titleWidget = Label("Adwaita Toasts")
        }

        window.content = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0).apply {
            append(header)
            append(overlay)
        }
    }
}

// test utility for a button that triggers a toast
private fun ToastOverlay.toastButton(
    buttonLabel: String,
    createToastFunc: () -> Toast
): Widget = Button(buttonLabel).apply {
    onClicked {
        this@toastButton.addToast(createToastFunc())
    }
}
