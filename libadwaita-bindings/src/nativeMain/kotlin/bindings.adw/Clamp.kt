package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Orientable
import bindings.gtk.Widget
import bindings.gtk.asWidget
import kotlinx.cinterop.CPointer
import native.adwaita.*
import native.gtk.GtkOrientable

class Clamp : Widget, Orientable {
    val adwClampPointer get() = gtkWidgetPointer.asTypedPointer<AdwClamp>()

    override val gtkOrientablePointer = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_clamp_new()!!)

    var child: Widget?
        get() = adw_clamp_get_child(adwClampPointer)?.asWidget()
        set(value) = adw_clamp_set_child(adwClampPointer, value?.gtkWidgetPointer)

    var maximumSize: Int
        get() = adw_clamp_get_maximum_size(adwClampPointer)
        set(value) = adw_clamp_set_maximum_size(adwClampPointer, value)

    var tighteningThreshold: Int
        get() = adw_clamp_get_tightening_threshold(adwClampPointer)
        set(value) = adw_clamp_set_tightening_threshold(adwClampPointer, value)
}