package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class ViewSwitcherTitle : Widget {
    val adwViewSwitcherTitlePointer get() = gtkWidgetPointer.asTypedPointer<AdwViewSwitcherTitle>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_view_switcher_title_new()!!)

    var stack: ViewStack?
        get() = adw_view_switcher_title_get_stack(adwViewSwitcherTitlePointer)?.asViewStack()
        set(value) = adw_view_switcher_title_set_stack(adwViewSwitcherTitlePointer, value?.adwViewStackPointer)

    var subtitle: String
        get() = adw_view_switcher_title_get_subtitle(adwViewSwitcherTitlePointer)?.toKString() ?: ""
        set(value) = adw_view_switcher_title_set_subtitle(adwViewSwitcherTitlePointer, value)

    var title: String
        get() = adw_view_switcher_title_get_title(adwViewSwitcherTitlePointer)?.toKString() ?: ""
        set(value) = adw_view_switcher_title_set_title(adwViewSwitcherTitlePointer, value)

    val titleVisible: Boolean get() = adw_view_switcher_title_get_title_visible(adwViewSwitcherTitlePointer).boolean

    var viewSwitcherEnabled: Boolean
        get() = adw_view_switcher_title_get_view_switcher_enabled(adwViewSwitcherTitlePointer).boolean
        set(value) = adw_view_switcher_title_set_view_switcher_enabled(adwViewSwitcherTitlePointer, value.gboolean)

}