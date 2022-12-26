package bindings.gtk

import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.GTK_TYPE_BUTTON

open class Button(pointer: CPointer<*>) : Widget(pointer) {

    constructor() : this(ButtonTypeInfo.newInstancePointer())

    companion object : ObjectCompanion<Button>(ButtonTypeInfo)
}

val ButtonTypeInfo = BuiltinTypeInfo(
    "GtkButton",
    GTK_TYPE_BUTTON,
    sizeOf<native.gtk.GtkButtonClass>(),
    sizeOf<native.gtk.GtkButton>(),
    ::Button
)
