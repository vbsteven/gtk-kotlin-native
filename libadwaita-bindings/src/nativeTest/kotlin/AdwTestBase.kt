import bindings.adw.Application
import bindings.adw.ApplicationWindow

open class AdwTestBase {

    fun runApplication(
        func: (Application) -> Unit
    ) {
        val application = Application("io.quantus.AdwTestApplication")

        application.onActivate {
            func(application)
        }
        application.runApplication()
    }

    fun runApplicationWindow(
        func: (ApplicationWindow) -> Unit
    ) = runApplication { app ->
        val window = ApplicationWindow(app)
        window.defaultSize = Pair(600, 400)
        func(window)
        window.show()
    }
}