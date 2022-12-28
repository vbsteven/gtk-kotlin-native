import bindings.adw.Application
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

@Ignore
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