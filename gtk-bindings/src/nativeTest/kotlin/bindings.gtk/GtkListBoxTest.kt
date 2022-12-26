package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Test

class GtkListBoxTest : GtkTestBase() {

    @Test
    fun testListBox() {
        runTestApplicationWindow { window ->

            val listBox = ListBox()
            val button1 = Button("Button 1")
            val button2 = Button("Button 2")
            val button3 = Button("Button 3")

            listBox.append(button1)
            listBox.append(button2)
            listBox.append(button3)

            window.child = listBox
        }
    }

}