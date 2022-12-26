package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Test

class GtkButtonTest : GtkTestBase() {

    @Test
    fun testButton() {
        runTestApplicationWindow { window ->

            val listBox = ListBox()

            listBox.appendAll(
                Button.newWithLabel("Button with Label"),
                Button.newWithMnemonic("Button with _Mnemonic"),
                Button.newFromIconName("open-menu"),
                Button().apply {
                    this.child = Label("Custom Label")
                },
                Button("Click me").apply {
                    onClicked { b ->
                        b.label = "You clicked!"
                    }
                }
            )

            window.child = listBox
        }
    }
}