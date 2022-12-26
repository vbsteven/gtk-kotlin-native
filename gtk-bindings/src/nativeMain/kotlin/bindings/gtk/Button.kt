package bindings.gtk

import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.GTK_TYPE_BUTTON
import native.gtk.gtk_button_new
import native.gtk.gtk_button_new_with_label

open class Button(pointer: CPointer<*>) : Widget(pointer) {

    constructor() : this(gtk_button_new()!!)
    constructor(label: String?) : this(gtk_button_new_with_label(label)!!)

    companion object : ObjectCompanion<Button>(ButtonTypeInfo)
}

val ButtonTypeInfo = BuiltinTypeInfo(
    "GtkButton",
    GTK_TYPE_BUTTON,
    sizeOf<native.gtk.GtkButtonClass>(),
    sizeOf<native.gtk.GtkButton>(),
    ::Button
)
