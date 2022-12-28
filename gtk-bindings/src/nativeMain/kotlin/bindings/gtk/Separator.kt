package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkOrientable
import native.gtk.GtkOrientation
import native.gtk.GtkSeparator
import native.gtk.gtk_separator_new

class Separator : Widget, Orientable {
    val gtkSeparatorPointer get() = gtkWidgetPointer.asTypedPointer<GtkSeparator>()

    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(orientation: GtkOrientation) : this(gtk_separator_new(orientation)!!)
}