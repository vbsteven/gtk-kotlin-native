package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class GtkLabelTests : GtkTestBase() {
    @Test
    fun createLabelWithText() {
        val label = Label("My Label")
        assertEquals("My Label", label.text)
    }

    @Test
    fun createEmptyLabel() {
        val label = Label()
        assertEquals("", label.text)
    }
}