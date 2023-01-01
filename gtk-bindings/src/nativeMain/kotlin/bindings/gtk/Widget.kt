package bindings.gtk

import bindings.glib.Variant
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.util.toCStringList
import bindings.util.toStringList
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gobject.g_strfreev
import native.gtk.*

open class Widget(pointer: CPointer<*>) : Object(pointer) {

    val gtkWidgetPointer: CPointer<GtkWidget> get() = gPointer.asTypedPointer<GtkWidget>()

    fun show() = gtk_widget_show(gtkWidgetPointer)
    fun hide() = gtk_widget_hide(gtkWidgetPointer)

    /* Activation */

    open fun activate(): Boolean = gtk_widget_activate(gtkWidgetPointer).boolean

    fun activateAction(name: String, arg: Variant?): Boolean =
        gtk_widget_activate_action_variant(gtkWidgetPointer, name, arg?.variantPointer).boolean

    fun activateDefault() = gtk_widget_activate_default(gtkWidgetPointer)


    /* Layout */

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


    /* Size */

    var sizeRequest: SizeRequest
        get() = memScoped {
            val widthVar = alloc<IntVar>()
            val heightVar = alloc<IntVar>()
            gtk_widget_get_size_request(gtkWidgetPointer, widthVar.ptr, heightVar.ptr)
            SizeRequest(widthVar.value, heightVar.value)
        }
        set(value) = gtk_widget_set_size_request(gtkWidgetPointer, value.width, value.height)

    val width: Int get() = gtk_widget_get_width(gtkWidgetPointer)
    val height: Int get() = gtk_widget_get_height(gtkWidgetPointer)


    /* Css */

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


    /* Margins */

    var marginBottom: Int
        get() = gtk_widget_get_margin_bottom(gtkWidgetPointer)
        set(value) = gtk_widget_set_margin_bottom(gtkWidgetPointer, value)

    var marginStart: Int
        get() = gtk_widget_get_margin_start(gtkWidgetPointer)
        set(value) = gtk_widget_set_margin_start(gtkWidgetPointer, value)

    var marginEnd: Int
        get() = gtk_widget_get_margin_end(gtkWidgetPointer)
        set(value) = gtk_widget_set_margin_end(gtkWidgetPointer, value)

    var marginTop: Int
        get() = gtk_widget_get_margin_top(gtkWidgetPointer)
        set(value) = gtk_widget_set_margin_top(gtkWidgetPointer, value)

    var opacity: Double
        get() = gtk_widget_get_opacity(gtkWidgetPointer)
        set(value) = gtk_widget_set_opacity(gtkWidgetPointer, value)


    /* Focus */

    var focusChild: Widget?
        get() = gtk_widget_get_focus_child(gtkWidgetPointer)?.asWidget()
        set(value) = gtk_widget_set_focus_child(gtkWidgetPointer, value?.gtkWidgetPointer)

    var focusOnClick: Boolean
        get() = gtk_widget_get_focus_on_click(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_focus_on_click(gtkWidgetPointer, value.gboolean)

    var focusable: Boolean
        get() = gtk_widget_get_focusable(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_focusable(gtkWidgetPointer, value.gboolean)

    fun grabFocus(): Boolean = gtk_widget_grab_focus(gtkWidgetPointer).boolean

    val isFocus: Boolean get() = gtk_widget_is_focus(gtkWidgetPointer).boolean

    /* Children */

    val firstChild: Widget? get() = gtk_widget_get_first_child(gtkWidgetPointer)?.asWidget()
    val lastChild: Widget? get() = gtk_widget_get_last_child(gtkWidgetPointer)?.asWidget()


    /* Other */

    /**
     * Tests if the point at x,y is contained in this widget.
     */
    fun contains(x: Double, y: Double): Boolean = gtk_widget_contains(gtkWidgetPointer, x, y).boolean

    val isMapped: Boolean get() = gtk_widget_get_mapped(gtkWidgetPointer).boolean

    val isSensitive: Boolean get() = gtk_widget_is_sensitive(gtkWidgetPointer).boolean

    val isDrawable: Boolean get() = gtk_widget_is_drawable(gtkWidgetPointer).boolean

    val inDestruction: Boolean get() = gtk_widget_in_destruction(gtkWidgetPointer).boolean

    var visible: Boolean
        get() = gtk_widget_get_visible(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_visible(gtkWidgetPointer, value.gboolean)

    var sensitive: Boolean
        get() = gtk_widget_get_sensitive(gtkWidgetPointer).boolean
        set(value) = gtk_widget_set_sensitive(gtkWidgetPointer, value.gboolean)

    fun getNative(): Native? = gtk_widget_get_native(gtkWidgetPointer)?.asNative()

    val hasDefault: Boolean
        get() = gtk_widget_has_default(gtkWidgetPointer).boolean

    fun errorBell() = gtk_widget_error_bell(gtkWidgetPointer)

    var overflow: GtkOverflow
        get() = gtk_widget_get_overflow(gtkWidgetPointer)
        set(value) = gtk_widget_set_overflow(gtkWidgetPointer, value)

    var tooltipText: String?
        get() = gtk_widget_get_tooltip_text(gtkWidgetPointer)?.toKString()
        set(value) = gtk_widget_set_tooltip_text(gtkWidgetPointer, value)

    var tooltipMarkup: String?
        get() = gtk_widget_get_tooltip_markup(gtkWidgetPointer)?.toKString()
        set(value) = gtk_widget_set_tooltip_markup(gtkWidgetPointer, value)

    data class SizeRequest(val width: Int, val height: Int)

    companion object {
        val Type = BuiltinTypeInfo(
            "GtkWidget",
            GTK_TYPE_WIDGET,
            sizeOf<GtkWidgetClass>(),
            sizeOf<GtkWidget>(),
            ::Widget,
        )
    }
}
