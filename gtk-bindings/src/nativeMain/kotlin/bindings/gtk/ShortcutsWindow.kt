package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkShortcutsWindow

class ShortcutsWindow : Window {
    val gtkShortcutsWindowPointer get() = gtkWindowPointer.asTypedPointer<GtkShortcutsWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)
}