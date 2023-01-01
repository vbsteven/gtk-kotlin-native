package customwidgetbinding

import bindings.gtk.*
import bindings.gtk.usertypes.WidgetClass
import bindings.gtk.usertypes.WidgetCompanion
import kotlinx.cinterop.CPointer
import native.gobject.G_BINDING_SYNC_CREATE
import native.gobject.G_PARAM_READWRITE
import native.gtk.GtkAlign.GTK_ALIGN_CENTER
import native.gtk.GtkOrientation.GTK_ORIENTATION_HORIZONTAL
import native.gtk.GtkOrientation.GTK_ORIENTATION_VERTICAL
import usertypes.intProperty

/**
 * An example showing how to create a Custom Widget with actions and properties
 * and bind the properties to other widgets.
 */
fun main() {
    val app = Application("io.quantus.gtk-kotlin-native.example.customwidgetbinding")
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
    // create the widgets
    val counterWidget = CounterWidget()
    val label = Label()

    // bind the "label" property of label to the "value" property of counterWidget
    counterWidget.bindProperty("value", label, "label", G_BINDING_SYNC_CREATE)

    // add the widgets to the layout
    window.child = Box(GTK_ORIENTATION_HORIZONTAL, 20).apply {
        append(counterWidget)
        append(label)
        homogeneous = true
    }
}


/**
 * A counter widget that has a [value] property and two buttons for incrementing and decrementing
 * the value.
 */
private class CounterWidget : Box {
    var value: Int by VALUE_PROPERTY

    private val incrementButton = Button("Increment").apply { actionName = "counter.increment" }
    private val decrementButton = Button("Decrement").apply { actionName = "counter.decrement" }

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(newInstancePointer()) {
        // configure the box
        orientation = GTK_ORIENTATION_VERTICAL
        spacing = 20
        valign = GTK_ALIGN_CENTER

        append(incrementButton)
        append(decrementButton)
    }

    private fun increment() {
        value++
    }

    private fun decrement() {
        value--
    }

    companion object : WidgetCompanion<CounterWidget>() {
        override val typeName = "CounterWidget"
        override val parentType = Box.typeInfo

        private val VALUE_PROPERTY = intProperty(
            CounterWidget::value,
            name = "value",
            nick = "value",
            blurb = "The value of this counter widget",
            minimum = 0,
            maximum = Int.MAX_VALUE,
            defaultValue = 0,
            flags = G_PARAM_READWRITE
        )


        override fun classInit(klass: WidgetClass<CounterWidget>) {
            klass.installAction("counter.increment", CounterWidget::increment)
            klass.installAction("counter.decrement", CounterWidget::decrement)
            klass.installProperty(VALUE_PROPERTY)
        }
    }
}