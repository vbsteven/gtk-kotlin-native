package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.GTK_TYPE_WIDGET
import native.gtk.GtkWidget
import native.gtk.GtkWidgetClass

open class Widget(pointer: CPointer<*>) : Object(pointer) {

    companion object : ObjectCompanion<Widget>(WidgetTypeInfo)
}

val WidgetTypeInfo = BuiltinTypeInfo(
    "GtkWidget",
    GTK_TYPE_WIDGET,
    sizeOf<GtkWidgetClass>(),
    sizeOf<GtkWidget>(),
    ::Widget,
)