package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class Widget(pointer: CPointer<*>) : Object(pointer) {

    @Suppress("UNCHECKED_CAST")
    val widgetPointer: CPointer<GtkWidget> get() = gPointer as CPointer<GtkWidget>

    fun show() = gtk_widget_show(widgetPointer)
    fun hide() = gtk_widget_hide(widgetPointer)

    companion object : ObjectCompanion<Widget>(WidgetTypeInfo)
}

val WidgetTypeInfo = BuiltinTypeInfo(
    "GtkWidget",
    GTK_TYPE_WIDGET,
    sizeOf<GtkWidgetClass>(),
    sizeOf<GtkWidget>(),
    ::Widget,
)

fun CPointer<GtkWidget>.asWidget(): Widget = Widget(this)