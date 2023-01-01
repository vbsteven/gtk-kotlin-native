package bindings.gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class Separator : Widget, Orientable {
    val gtkSeparatorPointer get() = gtkWidgetPointer.asTypedPointer<GtkSeparator>()

    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(orientation: GtkOrientation) : this(gtk_separator_new(orientation)!!)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_SEPARATOR, ::Separator)
    }
}