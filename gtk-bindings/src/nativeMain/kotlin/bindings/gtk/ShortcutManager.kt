package bindings.gtk

import kotlinx.cinterop.CPointer
import native.gtk.GtkShortcutManager

interface ShortcutManager {
    val gtkShortcutManagerPointer: CPointer<GtkShortcutManager>
}
