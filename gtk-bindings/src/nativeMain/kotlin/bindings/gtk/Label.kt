package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import native.gtk.*

open class Label(pointer: CPointer<*>) : Widget(pointer) {

    val gtkLabelPointer get() = gtkWidgetPointer.asTypedPointer<GtkLabel>()

    companion object : ObjectCompanion<Label>(LabelTypeInfo)

    constructor() : this(LabelTypeInfo.newInstancePointer())
    constructor(text: String?) : this(gtk_label_new(text)!!)

    var text: String
        get() = gtk_label_get_text(gPointer.reinterpret())?.toKString() ?: ""
        set(value) = gtk_label_set_text(gPointer.reinterpret(), value)

}

private val LabelTypeInfo = BuiltinTypeInfo(
    "GtkLabel",
    GTK_TYPE_LABEL,
    -1,
    -1,
    ::Label
)