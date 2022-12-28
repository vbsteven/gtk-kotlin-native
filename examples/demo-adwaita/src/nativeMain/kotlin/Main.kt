import bindings.adw.Application
import bindings.adw.ApplicationWindow
import bindings.adw.Flap
import bindings.adw.HeaderBar
import bindings.gio.Menu
import bindings.gio.SimpleAction
import bindings.gtk.*
import native.gobject.G_BINDING_BIDIRECTIONAL
import native.gtk.GtkOrientation


// hoist these variables up because the content is built in separate functions
// this makes them accessible for property binding
lateinit var sidebarToggle: ToggleButton
lateinit var flap: Flap


fun main() {
    val app = Application("com.example.adwaita.demo")
    app.onActivate {
        buildMainWindow(app).show()
    }

    // build actions
    app.addAction(SimpleAction("show-about").apply {
        onActivate { showAbout() }
    })
    app.addAction(SimpleAction("quit").apply {
        onActivate { app.quit() }
    })

    app.runApplication()
    app.unref()
}

fun buildMainWindow(app: Application) = ApplicationWindow(app).apply {
    defaultSize = Pair(1280, 720)

    content = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0).apply {
        append(buildTitleBar())
        append(buildContent())
    }
}

fun buildTitleBar() = HeaderBar().apply {
    titleWidget = Label("Adwaita Demo")

    sidebarToggle = ToggleButton()
    sidebarToggle.active = true
    sidebarToggle.iconName = "sidebar-show"
    packStart(sidebarToggle)

    val menuButton = MenuButton().apply {
        iconName = "open-menu"
        menuModel = buildMenu()
    }
    packEnd(menuButton)
}

fun buildContent(): Widget {

    val stack = Stack()
    val stackSidebar = StackSidebar()
    stackSidebar.stack = stack
    stackSidebar.sizeRequest = Widget.SizeRequest(200, -1)
    stackSidebar.vexpand = true
    stackSidebar.addCssClass("background")

    stack.hexpand = true

    stack.addTitled(buildButtonPage(), "Buttons", "Buttons")
    stack.addTitled(buildToastDemoPage(), "Toasts", "Toasts")
    stack.addTitled(buildAboutPage(), "About", "About")

    flap = Flap()
    flap.flap = stackSidebar
    flap.content = stack

    flap.bindProperty("reveal-flap", sidebarToggle, "active", G_BINDING_BIDIRECTIONAL)

    return flap
}

fun buildMenu(): Menu {
    val menu = Menu()
    menu.append("Close", "app.quit")
    menu.append("About Adwaita Demo", "app.show-about")

    return menu
}
