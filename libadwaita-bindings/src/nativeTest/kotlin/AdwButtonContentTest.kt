import bindings.adw.ButtonContent
import bindings.gtk.Button
import bindings.gtk.ListBox
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class AdwButtonContentTest : AdwTestBase() {

    @Test
    fun testButtonContent() = runApplicationWindow { window ->
        val listBox = ListBox()

        listBox.appendAll(
            Button().apply {
                child = ButtonContent("Hello Button", "emblem-music")
            },
            Button().apply {
                child = ButtonContent(iconName = "emblem-music")
            },
            Button().apply {
                child = ButtonContent().apply {
                    label = "Music"
                    iconName = "emblem-music"
                }
            },
        )

        window.content = listBox
    }

}