package bindings.gtk

import bindings.gio.Menu
import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class GtkMenuButtonTest : GtkTestBase() {

    @Test
    fun testMenuButton() {
        runTestApplicationWindow { window ->

            val menu = Menu()
            menu.append("Item 1")
            menu.append("Item 2")
            menu.append("Item 3")

            val section = Menu()
            section.append("Section Item 1")
            section.append("Section Item 2")
            menu.appendSection("My Section", section)

            val submenu = Menu()
            submenu.append("Submenu Item 1")
            submenu.append("Submenu Item 2")
            submenu.append("Submenu Item 3")
            menu.appendSubmenu("My Submenu", submenu)

            val menuButton = MenuButton()
            menuButton.label = "My Menu Button"
            menuButton.iconName = "open-menu"
            menuButton.menuModel = menu

            window.child = menuButton
        }
    }
}