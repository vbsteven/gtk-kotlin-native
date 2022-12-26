package bindings.gtk

import bindings.gio.Menu
import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Test

class GtkHeaderBartest : GtkTestBase() {

    @Test
    fun testHeaderBar() {
        runTestApplicationWindow { window ->
            val menu = Menu()
            menu.append("Some Item")

            val menuButton = MenuButton()
            menuButton.iconName = "open-menu"
            menuButton.menuModel = menu

            val title = Label("My Custom Title")

            val headerBar = HeaderBar()
            headerBar.packEnd(menuButton)
            headerBar.titleWidget = title

            window.titleBar = headerBar
            window.child = Label("Window content")
        }
    }
}