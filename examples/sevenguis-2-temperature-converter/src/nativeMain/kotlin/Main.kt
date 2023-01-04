import bindings.gtk.*
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_HORIZONTAL

fun main() {
    val app = Application("io.quantus.gtkkn.sevenguis.temperature-converter")
    app.onActivate {
        val window = ApplicationWindow(app).apply {
            title = "7GUIs Temperature Converter"
        }

        val celciusField = Entry()
        val fahrenheitField = Entry()

        var isEditing = false

        celciusField.onChanged {
            if (isEditing) return@onChanged
            celciusField.text.toIntOrNull()?.let { c ->
                isEditing = true
                fahrenheitField.text = (c * (9 / 5.toDouble()) + 32).toInt().toString()
                isEditing = false
            }
        }

        fahrenheitField.onChanged {
            if (isEditing) return@onChanged
            fahrenheitField.text.toIntOrNull()?.let { f ->
                isEditing = true
                celciusField.text = ((f - 32) * (5 / 9.toDouble())).toInt().toString()
                isEditing = false
            }
        }

        window.child = Box(GTK_ORIENTATION_HORIZONTAL, 10).apply {
            homogeneous = true
            valign = GTK_ALIGN_CENTER
            marginTop = 10
            marginBottom = 10
            marginStart = 10
            marginEnd = 10
            append(celciusField)
            append(Label("Celcius ="))
            append(fahrenheitField)
            append(Label("Fahrenheit"))
        }

        window.show()
    }
    app.run()
    app.unref()
}

