import bindings.gtk.*
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_VERTICAL

fun main() {
    val app = Application("com.example.app")

    var counter = 0

    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "Hello World"
        window.defaultSize = Pair(600, 400)

        val label = Label()

        val button = Button("Click me")
        button.onClicked {
            label.text = "You clicked ${++counter} times"
        }

        val box = Box(GTK_ORIENTATION_VERTICAL, 20)
        box.valign = GTK_ALIGN_CENTER
        box.append(button)
        box.append(label)

        window.child = box

        window.show()
    }

    app.runApplication()
    app.unref()
}