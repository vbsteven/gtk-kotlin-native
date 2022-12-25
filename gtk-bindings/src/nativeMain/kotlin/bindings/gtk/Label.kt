package bindings.gtk

import bindings.gobject.KGType
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import native.gobject.GType
import native.gtk.GTK_TYPE_LABEL
import native.gtk.gtk_label_get_text
import native.gtk.gtk_label_new
import native.gtk.gtk_label_set_text

open class Label(pointer: CPointer<*>) : Widget(pointer) {

    constructor() : this(newInstancePointer())
    constructor(text: String?) : this(gtk_label_new(text)!!)

    var text: String
        get() = gtk_label_get_text(gPointer.reinterpret())?.toKString() ?: ""
        set(value) = gtk_label_set_text(gPointer.reinterpret(), value)

    companion object : KGType() {
        override val gType: GType = GTK_TYPE_LABEL
        override fun newInstancePointer(): CPointer<*> {
            return gtk_label_new(null)!!
        }
    }
}