package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Orientable
import bindings.gtk.Scrollable
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*
import native.gtk.GtkOrientable
import native.gtk.GtkScrollable

open class ClampScrollable : Widget, Scrollable, Orientable {
    val adwClampScrollablePointer get() = gtkWidgetPointer.asTypedPointer<AdwClampScrollable>()

    override val gtkScrollablePointer get() = gtkWidgetPointer.asTypedPointer<GtkScrollable>()
    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_clamp_scrollable_new()!!)

    var child: Widget?
        get() = adw_clamp_scrollable_get_child(adwClampScrollablePointer)?.asWidget()
        set(value) = adw_clamp_scrollable_set_child(adwClampScrollablePointer, value?.gtkWidgetPointer)

    var maximumSize: Int
        get() = adw_clamp_scrollable_get_maximum_size(adwClampScrollablePointer)
        set(value) = adw_clamp_scrollable_set_maximum_size(adwClampScrollablePointer, value)

    var tighteningThreshold: Int
        get() = adw_clamp_scrollable_get_tightening_threshold(adwClampScrollablePointer)
        set(value) = adw_clamp_scrollable_set_tightening_threshold(adwClampScrollablePointer, value)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_CLAMP_SCROLLABLE, ::ClampScrollable)
    }
}