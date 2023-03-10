package bindings.gtk.testutils

import bindings.gtk.Application
import bindings.gtk.ApplicationWindow
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
        func: (ApplicationWindow) -> Unit
    ) {
        runTestApplication {
            val window = ApplicationWindow(it.app)
            window.title = this::class.simpleName
            window.application = it.app
            window.defaultSize = Pair(600, 400)

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
