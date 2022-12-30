package bindings.gtk

import bindings.gobject.boolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import native.gtk.*

interface Scrollable {
    val gtkScrollablePointer: CPointer<GtkScrollable>

    var hadjustment: Adjustment?
        get() = gtk_scrollable_get_hadjustment(gtkScrollablePointer)?.asAdjustment()
        set(value) = gtk_scrollable_set_hadjustment(gtkScrollablePointer, value?.gtkAdjustmentPointer)

    var vadjustment: Adjustment?
        get() = gtk_scrollable_get_vadjustment(gtkScrollablePointer)?.asAdjustment()
        set(value) = gtk_scrollable_set_vadjustment(gtkScrollablePointer, value?.gtkAdjustmentPointer)

    var hscrollPolicy: GtkScrollablePolicy
        get() = gtk_scrollable_get_hscroll_policy(gtkScrollablePointer)
        set(value) = gtk_scrollable_set_hscroll_policy(gtkScrollablePointer, value)

    var vscrollPolicy: GtkScrollablePolicy
        get() = gtk_scrollable_get_vscroll_policy(gtkScrollablePointer)
        set(value) = gtk_scrollable_set_vscroll_policy(gtkScrollablePointer, value)

    val border: Border?
        get() = memScoped {
            val gBorder = alloc<GtkBorder>()
            val success = gtk_scrollable_get_border(gtkScrollablePointer, gBorder.ptr).boolean
            if (success) Border(gBorder.left, gBorder.right, gBorder.top, gBorder.bottom)
            else null
        }
}