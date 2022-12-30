import bindings.adw.Bin
import bindings.gtk.Button
import bindings.gtk.ListBox
import bindings.gtk.usertypes.WidgetClass
import bindings.gtk.usertypes.WidgetCompanion
import kotlinx.cinterop.CPointer
import native.gobject.G_SIGNAL_RUN_FIRST
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class SubclassAdwBinTest : AdwTestBase() {

    @Test
    fun testCustomWidget() = runApplicationWindow { window ->

        val widget1 = MyWidget("Widget 1")
        val widget2 = MyWidget("Widget 2")

        widget1.connectSignal(MyWidget.TEST_SIGNAL) {
            println("### Received TEST_SIGNAL from widget1")
        }
        widget2.connectSignal(MyWidget.TEST_SIGNAL) {
            println("### RECEIVED TEST_SIGNAL from widget2")
        }

        val listBox = ListBox()
        listBox.append(widget1)
        listBox.append(widget2)

        window.setTestContent("Subclass Example", listBox)
    }

}

/**
 * A custom GtkWidget class that extends from the Adwaita Bin widget.
 *
 * The implementation sets a Button as the Bin child.
 * The button activates the "mywidgetbutton-clicked" action.
 * The action handler emits the test-signal signal.
 */
class MyWidget : Bin {

    var name: String = ""

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(name: String) : this(newInstancePointer()) {
        this.name = name

        this.child = Button(name).apply {
            actionName = "mywidgetbutton-clicked"
        }
    }


    private fun myButtonClickedAction() {
        emitSignal(TEST_SIGNAL)
    }

    companion object : WidgetCompanion<MyWidget>() {
        override val typeName = "MyWidget"
        override val parentType = Bin.typeInfo

        const val TEST_SIGNAL = "test-signal"

        override fun classInit(klass: WidgetClass<MyWidget>) {
            // install the signal
            klass.installSignal(TEST_SIGNAL, G_SIGNAL_RUN_FIRST)
            // install the action with the handler
            klass.installAction("mywidgetbutton-clicked", MyWidget::myButtonClickedAction)
        }
    }

}


