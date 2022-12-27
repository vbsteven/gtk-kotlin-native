import bindings.adw.ActionRow
import bindings.adw.PreferencesGroup
import bindings.adw.PreferencesPage
import bindings.adw.PreferencesWindow
import bindings.gtk.Button
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
        prefsGroup.add(pref1)
        prefsGroup.add(pref2)

        prefsPage.add(prefsGroup)
        prefsWindow.add(prefsPage)
        prefsWindow.show()
    }
}