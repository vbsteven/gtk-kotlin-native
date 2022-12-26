package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Test

class WindowTests : GtkTestBase() {
    @Test
    fun presentWindow() = runTestApplication { handle ->
        val window = Window()

        window.application = handle.app
        window.defaultSize = Pair(1280, 720)
        window.title = "My Window"
        window.child = Button("Click me")

        window.present()
    }
}