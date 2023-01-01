package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*

class ViewSwitcher : Widget {
    val adwViewSwitcherPointer get() = gtkWidgetPointer.asTypedPointer<AdwViewSwitcher>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_view_switcher_new()!!)

    var policy: AdwViewSwitcherPolicy
        get() = adw_view_switcher_get_policy(adwViewSwitcherPointer)
        set(value) = adw_view_switcher_set_policy(adwViewSwitcherPointer, value)

    var stack: ViewStack?
        get() = adw_view_switcher_get_stack(adwViewSwitcherPointer)?.asViewStack()
        set(value) = adw_view_switcher_set_stack(adwViewSwitcherPointer, value?.adwViewStackPointer)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_VIEW_SWITCHER, ::ViewSwitcher)
    }
}