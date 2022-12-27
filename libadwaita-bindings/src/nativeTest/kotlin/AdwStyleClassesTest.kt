import bindings.adw.ButtonContent
import bindings.gtk.Box
import bindings.gtk.Button
import bindings.gtk.Label
import bindings.gtk.ListBox
import native.gtk.GtkAlign
import native.gtk.GtkOrientation
import kotlin.test.Test

class AdwStyleClassesTest : AdwTestBase() {

    @Test
    fun testButtonStyleClasses() = runApplicationWindow { window ->

        val listBox = ListBox()
        listBox.vexpand = true

        listBox.appendAll(
            buttonWithClass("pill"),
            buttonWithClass("suggested-action"),
            buttonWithClass("destructive-action"),
            buttonWithClass("raised"),
            buttonWithClass("opaque"),
            buttonWithClass("flat"),
            buttonWithClass("circular"),
            buttonWithClass("pill", "suggested-action"),
            buttonWithClass("pill", "suggested-action", "flat"),
            buttonWithClass("frame"),
            buttonWithClass("osd", "pill"),
            linkedControls1(),
            linkedControls2(),
            toolbar(),
            toolbar().apply { addCssClass("osd") },
            Label("title-1").apply { addCssClass("title-1") },
            Label("title-2").apply { addCssClass("title-2") },
            Label("title-3").apply { addCssClass("title-3") },
            Label("title-4").apply { addCssClass("title-4") },
        )

        window.addCssClass("devel")

        window.setTestContent("Style classes test", listBox)
    }
}

private fun buttonWithClass(vararg className: String) =
    Button(className.joinToString(separator = ", "))
        .apply {
            className.forEach { addCssClass(it) }
        }

private fun linkedControls1() =
    Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 0).apply {
        addCssClass("linked")
        append(Button("Button 1"))
        append(Button("Button 2"))
    }

private fun linkedControls2() =
    Box(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0).apply {
        hexpand = false
        halign = GtkAlign.GTK_ALIGN_END
        addCssClass("linked")
        append(Button.newFromIconName("emblem-music"))
        append(Button.newFromIconName("emblem-settings"))
    }

private fun toolbar() =
    Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 0).apply {
        addCssClass("toolbar")
        append(Button.newFromIconName("emblem-music"))
        append(Button.newFromIconName("emblem-system"))
        append(Button().apply {
            child = ButtonContent("Open", "document-open")
        })
        append(Label("Label"))
        append(Button.newFromIconName("view-more").apply {
            halign = GtkAlign.GTK_ALIGN_END
            hexpand = true
        })
    }
