package bindings.gtk

import bindings.gio.Menu
import bindings.gio.SimpleAction
import bindings.gtk.testutils.GtkTestBase
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

    @Test
    fun startGtkApplicationWithMenu() {
        runTestApplication {
            val application = it.app

            // setup menu

            val menu = Menu()

            val fileMenu = Menu()
            fileMenu.append("New", "app.new")
            fileMenu.append("Open", "app.open")
            fileMenu.append("Close", "app.close")
            menu.appendSubmenu("File", fileMenu)

            val aboutMenu = Menu()
            aboutMenu.append("Show About", "app.about")
            menu.appendSubmenu("About", aboutMenu)

            application.menuBar = menu

            // setup actions

            val newAction = SimpleAction("new").apply {
                onActivate {
                    println("Action 'New' activated")
                }
            }

            val closeAction = SimpleAction("close").apply {
                onActivate {
                    application.quit()
                }
            }

            application.addAction(newAction)
            application.addAction(closeAction)

            // setup window

            val window = ApplicationWindow(application)
            window.application = application
            window.child = Label("Window content")
            window.showMenubar = true
            window.present()
        }
    }
}