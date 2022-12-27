package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class Widget(pointer: CPointer<*>) : Object(pointer) {

    val gtkWidgetPointer: CPointer<GtkWidget> get() = gPointer.asTypedPointer<GtkWidget>()

    fun show() = gtk_widget_show(gtkWidgetPointer)
    fun hide() = gtk_widget_hide(gtkWidgetPointer)

    companion object : ObjectCompanion<Widget>(WidgetTypeInfo)
}

private val WidgetTypeInfo = BuiltinTypeInfo(
    "GtkWidget",
    GTK_TYPE_WIDGET,
    sizeOf<GtkWidgetClass>(),
    sizeOf<GtkWidget>(),
    ::Widget,
)
