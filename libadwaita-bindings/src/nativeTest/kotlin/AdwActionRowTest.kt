import bindings.adw.ActionRow
import bindings.adw.Avatar
import bindings.adw.HeaderBar
import bindings.gtk.Box
import bindings.gtk.Button
import bindings.gtk.ListBox
import native.gtk.GtkAlign
import native.gtk.GtkOrientation
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class AdwActionRowTest : AdwTestBase() {

    @Test
    fun testAdwActionRows() = runApplicationWindow { window ->
        val listBox = ListBox()

        listBox.appendAll(
            ActionRow().apply {
                title = "Action Row 1"
            },
            ActionRow().apply {
                title = "Action Row 2"
                subtitle = "Action Row subtitle"
            },
            ActionRow().apply {
                title = "Action Row 3"
                subtitle = "Action Row subtitle"
                iconName = "open-menu"
            },
            ActionRow().apply {
                title = "Action Row 4"
                subtitle = "Action Row subtitle"
                addSuffix(Button("Suffix").apply {
                    valign = GtkAlign.GTK_ALIGN_CENTER
                })
            },
            ActionRow().apply {
                title = "Action Row 5"
                subtitle = "Action Row subtitle"
                addPrefix(Avatar(30, null, false))
            },
        )

        window.content = Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0).apply {
            append(HeaderBar())
            append(listBox)
        }
    }
}