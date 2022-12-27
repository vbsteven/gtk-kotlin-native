import bindings.gio.Application
import kotlin.test.Test

class GApplicationTest {
    @Test
    fun startApplication() {
        println("Starting application")
        val application = Application("io.quantus.testapplication")
        application.run()
    }
}