import bindings.adw.Clamp
import bindings.adw.StatusPage
import bindings.gtk.Button
import kotlin.test.Test

class AdwStatusPageTest : AdwTestBase() {
    @Test
    fun testAdwStatusPage() = runApplicationWindow { window ->

        val statusPage = StatusPage()

        statusPage.title = "My Page Title"
        statusPage.iconName = "emblem-music"
        statusPage.description = "A short description"

        statusPage.child = Clamp().apply {
            maximumSize = 100
            child = Button("A button")
        }

        window.content = statusPage
    }
}