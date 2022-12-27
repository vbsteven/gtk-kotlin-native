package bindings.gtk

import bindings.gobject.*
import bindings.util.toCStringList
import bindings.util.toStringList
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gobject.g_strfreev
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

    val cssName: String
        get() = gtk_widget_get_css_name(gtkWidgetPointer)?.toKString() ?: ""

    var cssClasses: List<String>
        get() = memScoped {
            val chars = gtk_widget_get_css_classes(gtkWidgetPointer)!!
            val result = chars.toStringList()
            g_strfreev(chars)
            return result
        }
        set(value) = memScoped {
            gtk_widget_set_css_classes(gtkWidgetPointer, value.toCStringList(this))
        }

    fun addCssClass(className: String) = gtk_widget_add_css_class(gtkWidgetPointer, className)

    fun hasCssClass(className: String): Boolean = gtk_widget_has_css_class(gtkWidgetPointer, className).boolean

    fun removeCssClass(className: String) = gtk_widget_remove_css_class(gtkWidgetPointer, className)

    companion object : ObjectCompanion<Widget>(WidgetTypeInfo)
}

private val WidgetTypeInfo = BuiltinTypeInfo(
    "GtkWidget",
    GTK_TYPE_WIDGET,
    sizeOf<GtkWidgetClass>(),
    sizeOf<GtkWidget>(),
    ::Widget,
)
