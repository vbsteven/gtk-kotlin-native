import bindings.adw.*
import bindings.gtk.Box
import bindings.gtk.Button
import native.gtk.GtkAlign
import native.gtk.GtkOrientation
import kotlin.test.Test

class AdwViewSwitcherTest : AdwTestBase() {

    @Test
    fun testAdwViewSwitcher() = runApplicationWindow { window ->

        val viewSwitcher = ViewSwitcher()
        val viewStack = ViewStack()
        viewSwitcher.stack = viewStack

        repeat(5) { index ->
            val widget = Button("Page $index")
            widget.valign = GtkAlign.GTK_ALIGN_CENTER
            widget.vexpand = true
            viewStack.add(widget, "Page $index", "page-$index", "emblem-music")
        }

        val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
        box.vexpand = true
        box.append(viewSwitcher)
        box.append(viewStack)

        window.setTestContent("ViewSwitcher test", box)
    }

    @Test
    fun testAdwViewSwitcherBar() = runApplicationWindow { window ->
        val viewSwitcherBar = ViewSwitcherBar()
        val viewStack = ViewStack()
        viewSwitcherBar.stack = viewStack

        repeat(5) { index ->
            val widget = Button("Page $index")
            widget.valign = GtkAlign.GTK_ALIGN_CENTER
            widget.vexpand = true
            viewStack.add(widget, "Page $index", "page-$index", "emblem-music")
        }

        val toggleButton = Button("Toggle switcher reveal").apply {
            onClicked {
                viewSwitcherBar.reveal = !viewSwitcherBar.reveal
            }
        }

        val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
        box.vexpand = true
        box.append(toggleButton)
        box.append(viewStack)
        box.append(viewSwitcherBar)

        window.setTestContent("View Switcher Bar test", box)
    }

    @Test
    fun testAdwViewSwitcherTitle() = runApplicationWindow { window ->
        val viewSwitcherTitle = ViewSwitcherTitle()
        val viewStack = ViewStack()
        viewSwitcherTitle.stack = viewStack

        repeat(5) { index ->
            val widget = Button("Page $index")
            widget.valign = GtkAlign.GTK_ALIGN_CENTER
            widget.vexpand = true
            viewStack.add(widget, "Page $index", "page-$index", "emblem-music")
        }

        val headerBar = HeaderBar()
        headerBar.titleWidget = viewSwitcherTitle

        val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
        box.append(headerBar)
        box.append(viewStack)

        window.content = box
    }

    @Test
    fun testAdwViewSwitcherTitleAndBarBinding() = runApplicationWindow { window ->
        val viewStack = ViewStack()

        val viewSwitcherBar = ViewSwitcherBar()
        val viewSwitcherTitle = ViewSwitcherTitle()

        viewSwitcherBar.stack = viewStack
        viewSwitcherTitle.stack = viewStack
        viewSwitcherTitle.title = "Example Title"

        repeat(3) { index ->
            val widget = Button("Page $index")
            widget.valign = GtkAlign.GTK_ALIGN_CENTER
            widget.vexpand = true
            viewStack.add(widget, "Page $index", "page-$index", "emblem-music")
        }

        val headerBar = HeaderBar()
        headerBar.titleWidget = viewSwitcherTitle

        val box = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0)
        box.append(headerBar)
        box.append(viewStack)
        box.append(viewSwitcherBar)

        viewSwitcherTitle.bindProperty("title-visible", viewSwitcherBar, "reveal")

        window.content = box
    }

}