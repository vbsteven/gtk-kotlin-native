package bindings.gtk

import kotlin.test.Test
import kotlin.test.assertFailsWith

class GtkWidgetTest : GtkTestBase() {
    @Test
    fun createWidget() {
        assertFailsWith(Error::class) {
            val widget = Widget.newInstancePointer()
        }
    }
}