import bindings.adw.Application
import bindings.adw.ApplicationWindow
import bindings.adw.HeaderBar
import bindings.gtk.*
import native.gtk.GtkOrientation

open class AdwTestBase {

    fun runApplication(
        func: (Application) -> Unit
    ) {
        val application = Application("io.quantus.AdwTestApplication")

        application.onActivate {
            func(application)
        }
        application.runApplication()
    }

    fun runApplicationWindow(
        func: (ApplicationWindow) -> Unit
    ) = runApplication { app ->
        val window = ApplicationWindow(app)
        window.defaultSize = Pair(600, 400)
        func(window)
        window.show()
    }
}

/**
 * Helper for setting the content of the window and wrapping it
 * with a proper header bar
 */
fun ApplicationWindow.setTestContent(title: String, widget: Widget) {
    val headerBar = HeaderBar()
    headerBar.titleWidget = Label(title)
    val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
    box.append(headerBar)
    box.append(widget)

    this.content = box
}

/**
 * Helper for adding a button with a handler to a listbox for testing purposes.
 */
fun ListBox.addButton(buttonLabel: String, func: () -> Unit) {
    val button = Button(buttonLabel)
    button.onClicked { func() }
    this.append(button)
}
