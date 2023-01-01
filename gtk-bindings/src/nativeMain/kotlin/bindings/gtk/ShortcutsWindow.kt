package bindings.gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.GTK_TYPE_SHORTCUTS_WINDOW
import native.gtk.GtkShortcutsWindow

class ShortcutsWindow : Window {
    val gtkShortcutsWindowPointer get() = gtkWindowPointer.asTypedPointer<GtkShortcutsWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_SHORTCUTS_WINDOW, ::ShortcutsWindow)
    }
}