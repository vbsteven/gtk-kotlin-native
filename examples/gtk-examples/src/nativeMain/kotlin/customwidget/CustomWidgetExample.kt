package customwidget

import bindings.gtk.*
import bindings.gtk.usertypes.WidgetCompanion
import kotlinx.cinterop.CPointer
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_VERTICAL

/**
 * An example showing how to create a Custom Widget.
 */
fun main() {
    val app = Application("io.quantus.gtk-kotlin-native.example.customwidget")
    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "Custom Widget Example"
        window.defaultSize = Pair(600, 400)
        buildUI(window)
        window.show()
    }
    app.run()
    app.unref()
}

private fun buildUI(window: Window) {
    window.child = MyCustomWidget("LabelText", "open-menu")
}

/**
 * A simple custom Widget that extends the [Box] class and contains a [Label] and an [Image].
 */
private class MyCustomWidget : Box {

    private val label = Label()
    private val image = Image()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(text: String, iconName: String) : this(newInstancePointer()) {
        // configure the box
        orientation = GTK_ORIENTATION_VERTICAL
        spacing = 10
        marginStart = 10
        marginEnd = 10
        marginTop = 10
        marginBottom = 10

        vexpand = true
        hexpand = true
        valign = GTK_ALIGN_CENTER
        halign = GTK_ALIGN_CENTER

        // set values on the child widgets
        label.text = text
        image.iconName = iconName

        // add the child widgets
        append(image)
        append(label)
    }

    companion object : WidgetCompanion<MyCustomWidget>() {
        override val typeName = "MyCustomWidget"
        override val parentType = Box.typeInfo
    }
}