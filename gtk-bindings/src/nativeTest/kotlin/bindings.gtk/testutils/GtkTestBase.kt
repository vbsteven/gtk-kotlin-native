package bindings.gtk.testutils

import bindings.gtk.Application
import bindings.gtk.Window
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

    /**
     * Create a GTK [Application] and a [Window].
     * When the window is presented, the given [func] will run.
     */
    fun runTestApplicationWindow(
        func: (Window) -> Unit
    ) {
        runTestApplication {
            val window = Window()
            window.title = this::class.simpleName
            window.application = it.app


            func(window)
            window.present()
        }
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
