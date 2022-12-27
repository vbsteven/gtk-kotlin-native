import bindings.adw.StatusPage
import bindings.adw.TabBar
import bindings.adw.TabView
import bindings.gtk.Box
import bindings.gtk.Button
import bindings.gtk.Widget
import native.gtk.GtkOrientation
import kotlin.test.Test

class AdwTabBarTest : AdwTestBase() {

    @Test
    fun testTabBar() = runApplicationWindow { window ->
        val tabView = TabView()
        var tabCount = 0

        fun addTab(pinned: Boolean = false) {
            if (pinned) {
                tabView.appendPinned(testPageWidget("Tab ${++tabCount}")).apply {
                    title = "Tab $tabCount"
                    tooltip = "Tooltip $tabCount"
                }
            } else {
                tabView.append(testPageWidget("Tab ${++tabCount}")).apply {
                    title = "Tab $tabCount"
                    tooltip = "Tooltip $tabCount"
                }
            }
        }

        addTab()
        addTab()
        addTab()

        val tabBar = TabBar()
        tabBar.view = tabView

        val buttonBar = Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 10)
        buttonBar.append(Button("Add Tab").apply {
            onClicked { addTab() }
        })
        buttonBar.append(Button("Add Pinned Tab").apply {
            onClicked { addTab(true) }
        })

        val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
        box.append(buttonBar)
        box.append(tabBar)
        box.append(tabView)

        window.setTestContent("TabBar Test", box)
    }
}

private fun testPageWidget(pageTitle: String): Widget {
    return StatusPage().apply {
        title = pageTitle
        iconName = "emblem-music"
    }
}