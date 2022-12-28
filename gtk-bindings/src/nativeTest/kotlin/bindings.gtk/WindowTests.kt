package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore
class WindowTests : GtkTestBase() {
    @Test
    fun presentWindow() = runTestApplication { handle ->
        val window = Window()

        window.application = handle.app
        window.defaultSize = Pair(1280, 720)
        window.title = "My Window"
        window.child = Button("Click me")

        window.present()

        assertEquals(1, Window.toplevels.itemCount)
    }
}