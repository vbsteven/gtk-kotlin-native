package bindings.gtk

import bindings.gobject.KGType
import bindings.gobject.Object
import kotlinx.cinterop.CPointer
import native.gobject.GType
import native.gtk.GTK_TYPE_WIDGET

abstract class Widget(pointer: CPointer<*>) : Object(pointer) {

    companion object : KGType() {
        override val gType: GType = GTK_TYPE_WIDGET
        override fun newInstancePointer(): CPointer<*> {
            throw Error("GtkWidget cannot be instantiated")
        }
    }

}