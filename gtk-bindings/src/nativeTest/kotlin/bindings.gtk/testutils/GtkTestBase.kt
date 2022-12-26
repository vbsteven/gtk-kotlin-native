package bindings.gtk.testutils

import bindings.gtk.Application
import native.gtk.gtk_init

open class GtkTestBase {
    init {
        gtk_init()
    }

    /**
     * Create a GtkApplication and run the given [onActivateFunc]
     * when the application activates
     */
    fun runTestApplication(
        applicationId: String = "io.quantus.gtktestbaseapplication",
        onActivateFunc: (TestApplicationHandle) -> Unit
    ) {
        val application = Application(applicationId)
        application.onActivate { onActivateFunc(TestApplicationHandle(application)) }
        application.runApplication()
    }
}

/**
 * A handle for a running test [Application].
 *
 * The handle can be used to close the application.
 */
class TestApplicationHandle(
    val app: Application
)
