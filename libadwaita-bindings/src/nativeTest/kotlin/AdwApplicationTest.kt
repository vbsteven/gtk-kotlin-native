import bindings.adw.Application
import kotlin.test.Test
import kotlin.test.assertTrue

class AdwApplicationTest {
    @Test
    fun testAdwApplication() {
        var onActivateCalled = false

        val application = Application("io.quantus.adwtestapplication")
        application.onActivate {
            onActivateCalled = true
        }
        application.runApplication()

        assertTrue(onActivateCalled, "Application onActivate was not called")
    }
}