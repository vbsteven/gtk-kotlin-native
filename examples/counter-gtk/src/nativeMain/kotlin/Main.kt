import bindings.gtk.*
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_VERTICAL

fun main() {
    val app = Application("com.example.counter")

    var counter = 0

    app.onActivate {
        // create the window
        val window = ApplicationWindow(app)
        window.title = "Hello World"
        window.defaultSize = Pair(600, 400)

        // create the widgets
        val label = Label()
        val button = Button("Click me")

        // attach a button click handler
        button.onClicked {
            label.text = "You clicked ${++counter} times"
        }

        // wrap in a box for layout
        window.child = Box(GTK_ORIENTATION_VERTICAL, 20).apply {
            valign = GTK_ALIGN_CENTER
            append(button)
            append(label)
        }

        // show the window
        window.show()
    }

    app.runApplication()
    app.unref()
}