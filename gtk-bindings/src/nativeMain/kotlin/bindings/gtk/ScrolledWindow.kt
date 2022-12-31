package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import native.gtk.*

class ScrolledWindow : Widget {
    val gtkScrolledWindowPointer get() = gtkWidgetPointer.asTypedPointer<GtkScrolledWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_scrolled_window_new()!!)

    var child: Widget?
        get() = gtk_scrolled_window_get_child(gtkScrolledWindowPointer)?.asWidget()
        set(value) = gtk_scrolled_window_set_child(gtkScrolledWindowPointer, value?.gtkWidgetPointer)

    var hadjustment: Adjustment
        get() = gtk_scrolled_window_get_hadjustment(gtkScrolledWindowPointer)!!.asAdjustment()
        set(value) = gtk_scrolled_window_set_hadjustment(gtkScrolledWindowPointer, value.gtkAdjustmentPointer)

    var vadjustment: Adjustment
        get() = gtk_scrolled_window_get_vadjustment(gtkScrolledWindowPointer)!!.asAdjustment()
        set(value) = gtk_scrolled_window_set_vadjustment(gtkScrolledWindowPointer, value.gtkAdjustmentPointer)

    var hasFrame: Boolean
        get() = gtk_scrolled_window_get_has_frame(gtkScrolledWindowPointer).boolean
        set(value) = gtk_scrolled_window_set_has_frame(gtkScrolledWindowPointer, value.gboolean)

    val hscrollbar: Widget
        get() = gtk_scrolled_window_get_hscrollbar(gtkScrolledWindowPointer)!!.asWidget()

    val vscrollbar: Widget
        get() = gtk_scrolled_window_get_vscrollbar(gtkScrolledWindowPointer)!!.asWidget()

    var kineticScrolling: Boolean
        get() = gtk_scrolled_window_get_kinetic_scrolling(gtkScrolledWindowPointer).boolean
        set(value) = gtk_scrolled_window_set_kinetic_scrolling(gtkScrolledWindowPointer, value.gboolean)

    var maxContentHeight: Int
        get() = gtk_scrolled_window_get_max_content_height(gtkScrolledWindowPointer)
        set(value) = gtk_scrolled_window_set_max_content_height(gtkScrolledWindowPointer, value)

    var maxContentWidth: Int
        get() = gtk_scrolled_window_get_max_content_width(gtkScrolledWindowPointer)
        set(value) = gtk_scrolled_window_set_max_content_width(gtkScrolledWindowPointer, value)

    var minContentHeight: Int
        get() = gtk_scrolled_window_get_min_content_height(gtkScrolledWindowPointer)
        set(value) = gtk_scrolled_window_set_min_content_height(gtkScrolledWindowPointer, value)

    var minContentWidth: Int
        get() = gtk_scrolled_window_get_min_content_width(gtkScrolledWindowPointer)
        set(value) = gtk_scrolled_window_set_min_content_width(gtkScrolledWindowPointer, value)

    var overlayScrolling: Boolean
        get() = gtk_scrolled_window_get_overlay_scrolling(gtkScrolledWindowPointer).boolean
        set(value) = gtk_scrolled_window_set_overlay_scrolling(gtkScrolledWindowPointer, value.gboolean)

    var placement: GtkCornerType
        get() = gtk_scrolled_window_get_placement(gtkScrolledWindowPointer)
        set(value) = gtk_scrolled_window_set_placement(gtkScrolledWindowPointer, value)

    var policy: ScrollbarPolicy
        get() = memScoped {
            val hpolicy = alloc<GtkPolicyType.Var>()
            val vpolicy = alloc<GtkPolicyType.Var>()
            gtk_scrolled_window_get_policy(gtkScrolledWindowPointer, hpolicy.ptr, vpolicy.ptr)
            ScrollbarPolicy(hpolicy.value, vpolicy.value)
        }
        set(value) = gtk_scrolled_window_set_policy(gtkScrolledWindowPointer, value.horizontal, value.vertical)

    data class ScrollbarPolicy(val horizontal: GtkPolicyType, val vertical: GtkPolicyType)

    var propagataNaturalHeight: Boolean
        get() = gtk_scrolled_window_get_propagate_natural_height(gtkScrolledWindowPointer).boolean
        set(value) = gtk_scrolled_window_set_propagate_natural_height(gtkScrolledWindowPointer, value.gboolean)

    var propagataNaturalWidth: Boolean
        get() = gtk_scrolled_window_get_propagate_natural_width(gtkScrolledWindowPointer).boolean
        set(value) = gtk_scrolled_window_set_propagate_natural_width(gtkScrolledWindowPointer, value.gboolean)

    fun unsetPlacement() = gtk_scrolled_window_unset_placement(gtkScrolledWindowPointer)

}