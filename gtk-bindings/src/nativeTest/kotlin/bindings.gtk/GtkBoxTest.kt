package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import native.gtk.GtkOrientation
import kotlin.test.Test

class GtkBoxTest : GtkTestBase() {

    @Test
    fun testBox() {
        runTestApplicationWindow { window ->

            val box = Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 0)
            box.appendAll(Button("Button 1"), Button("Button 2"))
            box.homogeneous = true

            window.child = box
        }
    }
}