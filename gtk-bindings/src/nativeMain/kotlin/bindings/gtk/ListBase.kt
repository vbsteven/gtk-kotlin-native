package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkOrientable
import native.gtk.GtkScrollable

open class ListBase : Widget, Orientable, Scrollable {
    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()
    override val gtkScrollablePointer get() = gtkWidgetPointer.asTypedPointer<GtkScrollable>()

    constructor(pointer: CPointer<*>) : super(pointer)
}