import bindings.gio.GApplication
import kotlin.test.Test

class GApplicationTest {
    @Test
    fun startApplication() {
        println("Starting application")
        val application = GApplication("io.quantus.testapplication")
        application.run()
    }
}