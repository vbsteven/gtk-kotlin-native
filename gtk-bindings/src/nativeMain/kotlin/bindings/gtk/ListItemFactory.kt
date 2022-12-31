package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkListItemFactory

open class ListItemFactory : Object {
    val gtkListItemFactoryPointer get() = gPointer.asTypedPointer<GtkListItemFactory>()

    constructor(pointer: CPointer<*>) : super(pointer)
}