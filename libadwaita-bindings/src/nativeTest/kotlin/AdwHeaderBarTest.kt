import bindings.adw.Application
import bindings.adw.ApplicationWindow
import bindings.adw.HeaderBar
import bindings.gtk.Box
import bindings.gtk.Button
import bindings.gtk.Label
import native.gtk.GtkOrientation
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class AdwHeaderBarTest {
    @Test
    fun testAdwHeaderBar() {
        val application = Application("io.quantus.adwheaderbartest")
        application.onActivate {

            // build the headerbar
            val headerBar = HeaderBar()
            headerBar.titleWidget = Label("Custom Title Widget")
            headerBar.packStart(Button("Test Start"))
            headerBar.packEnd(Button("Test End"))

            val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
            box.append(headerBar)

            val window = ApplicationWindow(application)
            window.defaultSize = Pair(1280, 720)
            window.content = box

            window.show()
        }
        application.runApplication()
    }
}