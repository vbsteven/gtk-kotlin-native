package bindings.gtk

import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import native.gtk.GTK_TYPE_LABEL
import native.gtk.gtk_label_get_text
import native.gtk.gtk_label_new
import native.gtk.gtk_label_set_text

open class Label(pointer: CPointer<*>) : Widget(pointer) {

    companion object : ObjectCompanion<Label>(LabelTypeInfo)

    constructor() : this(LabelTypeInfo.newInstancePointer())
    constructor(text: String?) : this(gtk_label_new(text)!!)

    var text: String
        get() = gtk_label_get_text(gPointer.reinterpret())?.toKString() ?: ""
        set(value) = gtk_label_set_text(gPointer.reinterpret(), value)

}

val LabelTypeInfo = BuiltinTypeInfo(
    "GtkLabel",
    GTK_TYPE_LABEL,
    -1,
    -1,
    ::Label
)