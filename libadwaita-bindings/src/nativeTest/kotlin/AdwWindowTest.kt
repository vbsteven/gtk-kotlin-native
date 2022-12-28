import bindings.adw.Application
import bindings.adw.Window
import bindings.gtk.Button
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class AdwWindowTest {
    @Test
    fun testAdwWindow() {
        val application = Application("io.quantus.adwtestapplication")
        application.onActivate {
            val window = Window()
            window.defaultSize = Pair(1280, 720)
            window.application = application

            window.content = Button("Hello")

            window.show()
        }
        application.runApplication()
    }
}