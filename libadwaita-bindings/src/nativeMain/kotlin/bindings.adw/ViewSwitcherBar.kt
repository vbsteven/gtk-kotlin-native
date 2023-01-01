package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*

class ViewSwitcherBar : Widget {
    val adwViewSwitcherBarPointer get() = gtkWidgetPointer.asTypedPointer<AdwViewSwitcherBar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_view_switcher_bar_new()!!)


    var reveal: Boolean
        get() = adw_view_switcher_bar_get_reveal(adwViewSwitcherBarPointer).boolean
        set(value) = adw_view_switcher_bar_set_reveal(adwViewSwitcherBarPointer, value.gboolean)

    var stack: ViewStack?
        get() = adw_view_switcher_bar_get_stack(adwViewSwitcherBarPointer)?.asViewStack()
        set(value) = adw_view_switcher_bar_set_stack(adwViewSwitcherBarPointer, value?.adwViewStackPointer)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_VIEW_SWITCHER_BAR, ::ViewSwitcherBar)
    }
}