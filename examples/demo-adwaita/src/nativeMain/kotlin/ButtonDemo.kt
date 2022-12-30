import bindings.adw.*
import bindings.gio.Menu
import bindings.gio.MenuItem
import bindings.gio.SimplePermission
import bindings.gtk.*
import native.gtk.GtkAlign.GTK_ALIGN_CENTER

fun buildButtonPage() = StatusPage().apply {
    title = "Buttons"
    child = Clamp().apply {
        maximumSize = 800
        child = PreferencesGroup().apply {
            addButton("Simple button") {
                Button(it)
            }
            addButton("Pill button") {
                Button(it).apply { addCssClass("pill") }
            }
            addButton("Raised") {
                Button(it).apply { addCssClass("raised") }
            }
            addButton("Flat") {
                Button(it).apply { addCssClass("flat") }
            }
            addButton("Suggested Action") {
                Button(it).apply { addCssClass("suggested-action") }
            }
            addButton("Destructive Action") {
                Button(it).apply { addCssClass("destructive-action") }
            }
            addButton("Icon") {
                Button.newFromIconName("open-menu")
            }
            addButton("Circular Icon") {
                Button.newFromIconName("open-menu").apply { addCssClass("circular") }
            }
            addButton("OSD") {
                Button(it).apply { addCssClass("osd") }
            }
            addButton("ButtonContent") {
                Button(it).apply {
                    child = ButtonContent(it, "open-menu")
                }
            }
            addButton("Split button") {
                SplitButton().apply {
                    label = "Open"
                    menuModel = Menu().apply {
                        this.appendItem(MenuItem("Close"))
                        this.appendItem(MenuItem("Another"))
                    }
                    dropdownTooltip = "Dropdown tooltip text"
                }
            }
            addButton("Split button Icon") {
                SplitButton().apply {
                    iconName = "menu-open"
                    menuModel = Menu().apply {
                        this.appendItem(MenuItem("Close"))
                        this.appendItem(MenuItem("Another"))
                    }
                }
            }
            addButton("Split button Content") {
                SplitButton().apply {
                    child = ButtonContent().apply {
                        label = "Open"
                        iconName = "open-menu"
                    }
                    menuModel = Menu().apply {
                        this.appendItem(MenuItem("Close"))
                        this.appendItem(MenuItem("Another"))
                    }
                }
            }
            addButton("Check Button") {
                CheckButton()
            }
            addButton("Check Button Label") {
                CheckButton("Label")
            }
            addButton("Check Button Selection mode") {
                CheckButton().apply { addCssClass("selection-mode") }
            }
            addButton("Lock button") {
                LockButton(SimplePermission(false))
            }
            addButton("Link Button") {
                LinkButton("https://github.com/vbsteven/gtk-kotlin-native")
            }
            addButton("Link Button Label") {
                LinkButton("https://github.com/vbsteven/gtk-kotlin-native", "Github")
            }
        }
    }
}

private fun PreferencesGroup.addButton(label: String, func: (String) -> Widget) {
    add(ActionRow().apply {
        title = label
        addSuffix(func(label).apply { valign = GTK_ALIGN_CENTER })
    })
}


