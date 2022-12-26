package bindings.gtk

import kotlin.test.Test
import kotlin.test.assertTrue

class GtkApplicationTest : GtkTestBase() {
    @Test
    fun startGtkApplication() {
        var activated = false

        val application = Application("io.quantus.gtkapplication")
        application.onActivate {
            activated = true
            application.quit()
        }
        application.runApplication()
        assertTrue(activated, "Application was not activated")
    }
}