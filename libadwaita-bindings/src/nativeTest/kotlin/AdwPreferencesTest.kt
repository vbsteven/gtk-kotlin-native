import bindings.adw.*
import bindings.gtk.Button
import bindings.gtk.Label
import native.gtk.GtkAlign
import kotlin.test.Test

class AdwPreferencesTest : AdwTestBase() {
    @Test
    fun testPreferences() = runApplication { app ->

        val prefsWindow = PreferencesWindow()
        prefsWindow.application = app

        val prefsPage = PreferencesPage()
        prefsPage.name = "Page 1"
        prefsPage.title = "Page 1 title"
        prefsPage.iconName = "emblem-settings"

        val prefsGroup = PreferencesGroup()
        prefsGroup.title = "Group 1"
        prefsGroup.description = "Description for group 1"
        prefsGroup.headerSuffix = Button("Group 1 Button").apply {
            vexpand = false
            valign = GtkAlign.GTK_ALIGN_CENTER
        }

        val pref1 = ActionRow().apply {
            title = "Preference 1"
            subtitle = "Short description for 1"
            iconName = "emblem-music"
        }
        val pref2 = ActionRow().apply {
            title = "Preference 2"
            subtitle = "Short description for 2"
        }

        val entryPref = EntryRow()
        entryPref.title = "Name"
        entryPref.text = "Your name"
        entryPref.showApplyButton = true
        entryPref.onApply {
            val text = entryPref.text
            if (text.isNotEmpty())
                prefsWindow.addToast(Toast("Hello ${entryPref.text}"))
        }
        entryPref.onChanged {
            println("EntryPref emitted changed signal with text: ${entryPref.text}")
        }

        val passwordPref = PasswordEntryRow()
        passwordPref.title = "Password"

        val expanderRow = ExpanderRow()

        expanderRow.addPrefix(Label("Prefix"))
        expanderRow.addRow(Label("Row 1"))
        expanderRow.addRow(Label("Row 2"))
        expanderRow.addRow(ActionRow().apply {
            title = "Expanded action row"
            subtitle = "Some subtitle"
        })
        expanderRow.addAction(Label("Action"))

        prefsGroup.add(pref1)
        prefsGroup.add(pref2)
        prefsGroup.add(entryPref)
        prefsGroup.add(passwordPref)
        prefsGroup.add(expanderRow)

        prefsPage.add(prefsGroup)
        prefsWindow.add(prefsPage)
        prefsWindow.show()
    }
}