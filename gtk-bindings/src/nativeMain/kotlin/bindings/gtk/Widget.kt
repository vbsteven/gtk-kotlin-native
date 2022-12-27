package bindings.gtk

import bindings.gobject.*
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class Widget(pointer: CPointer<*>) : Object(pointer) {

    val gtkWidgetPointer: CPointer<GtkWidget> get() = gPointer.asTypedPointer<GtkWidget>()

    fun show() = gtk_widget_show(gtkWidgetPointer)
    fun hide() = gtk_widget_hide(gtkWidgetPointer)

    var hexpand: Boolean
        get() = gtk_widget_get_hexpand(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_hexpand(gtkWidgetPointer, value.gboolean)

    var vexpand: Boolean
        get() = gtk_widget_get_vexpand(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_vexpand(gtkWidgetPointer, value.gboolean)

    var valign: GtkAlign
        get() = gtk_widget_get_valign(gtkWidgetPointer)
        set(value) = gtk_widget_set_valign(gtkWidgetPointer, value)

    var halign: GtkAlign
        get() = gtk_widget_get_halign(gtkWidgetPointer)
        set(value) = gtk_widget_set_halign(gtkWidgetPointer, value)


    companion object : ObjectCompanion<Widget>(WidgetTypeInfo)
}

private val WidgetTypeInfo = BuiltinTypeInfo(
    "GtkWidget",
    GTK_TYPE_WIDGET,
    sizeOf<GtkWidgetClass>(),
    sizeOf<GtkWidget>(),
    ::Widget,
)
