import bindings.gtk.*
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_HORIZONTAL

fun main() {
    val app = Application("io.quantus.gtkkn.sevenguis.counter")
    app.onActivate {
        val window = ApplicationWindow(app).apply {
            title = "7GUIs Counter"
        }

        var counter = 0

        val textField = Entry()
        textField.text = counter.toString()

        val button = Button("Count").apply {
            onClicked {
                counter++
                textField.text = counter.toString()
            }
        }

        window.child = Box(GTK_ORIENTATION_HORIZONTAL, 10).apply {
            homogeneous = true
            valign = GTK_ALIGN_CENTER
            marginTop = 10
            marginBottom = 10
            marginStart = 10
            marginEnd = 10
            append(textField)
            append(button)
        }

        window.show()
    }
    app.run()
    app.unref()
}

