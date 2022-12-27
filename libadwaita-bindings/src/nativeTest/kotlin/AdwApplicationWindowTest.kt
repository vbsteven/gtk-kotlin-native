import bindings.adw.Application
import bindings.adw.ApplicationWindow
import bindings.gtk.Button
import kotlin.test.Test

class AdwApplicationWindowTest {
    @Test
    fun testAdwApplicationWindow() {

        val application = Application("io.quantus.adwtestapplication")
        application.onActivate {
            val window = ApplicationWindow(application)
            window.defaultSize = Pair(1280, 720)

            window.content = Button("Hello AdwApplicationWindow")

            window.show()
        }
        application.runApplication()
    }
}