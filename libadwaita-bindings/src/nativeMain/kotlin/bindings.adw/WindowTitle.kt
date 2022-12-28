package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class WindowTitle : Widget {
    val adwWindowTitlePointer get() = gtkWidgetPointer.asTypedPointer<AdwWindowTitle>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(title: String = "", subtitle: String = "") : this(adw_window_title_new(title, subtitle)!!)

    var title: String
        get() = adw_window_title_get_title(adwWindowTitlePointer)?.toKString() ?: ""
        set(value) = adw_window_title_set_title(adwWindowTitlePointer, value)

    var subtitle: String
        get() = adw_window_title_get_subtitle(adwWindowTitlePointer)?.toKString() ?: ""
        set(value) = adw_window_title_set_subtitle(adwWindowTitlePointer, value)

}