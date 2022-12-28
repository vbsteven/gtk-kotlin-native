import bindings.gtk.Application
import bindings.gtk.ApplicationWindow
import bindings.gtk.Label

fun main() {
    val app = Application("com.example.app")

    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "Hello World"
        window.defaultSize = Pair(600, 400)

        window.child = Label("Hello from Kotlin/Native")

        window.show()
    }

    app.runApplication()
    app.unref()
}