import bindings.adw.Avatar
import bindings.adw.HeaderBar
import bindings.gtk.Box
import bindings.gtk.ListBox
import native.gtk.GtkOrientation
import kotlin.test.Test

class AdwAvatarTest : AdwTestBase() {
    @Test
    fun testAvatar() = runApplicationWindow { window ->
        val listBox = ListBox()

        listBox.appendAll(
            Avatar(50, null, false),
            Avatar(50, "Steven Van Bael", false),
            Avatar(50, "Steven Van Bael", true),
            Avatar(100, "Steven Van Bael", true),
            Avatar(100, null, false).apply {
                iconName = "open-menu"
            }
        )

        window.content = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0).apply {
            append(HeaderBar())
            append(listBox)
        }
    }
}