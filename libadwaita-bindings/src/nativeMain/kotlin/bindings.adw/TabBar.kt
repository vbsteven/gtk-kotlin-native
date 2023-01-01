package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*

class TabBar : Widget {
    val adwTabBarPointer get() = gtkWidgetPointer.asTypedPointer<AdwTabBar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_tab_bar_new()!!)

    var autohide: Boolean
        get() = adw_tab_bar_get_autohide(adwTabBarPointer).boolean
        set(value) = adw_tab_bar_set_autohide(adwTabBarPointer, value.gboolean)

    var endActionWidget: Widget?
        get() = adw_tab_bar_get_end_action_widget(adwTabBarPointer)?.asWidget()
        set(value) = adw_tab_bar_set_end_action_widget(adwTabBarPointer, value?.gtkWidgetPointer)

    var expandTabs: Boolean
        get() = adw_tab_bar_get_expand_tabs(adwTabBarPointer).boolean
        set(value) = adw_tab_bar_set_expand_tabs(adwTabBarPointer, value.gboolean)

    var inverted: Boolean
        get() = adw_tab_bar_get_inverted(adwTabBarPointer).boolean
        set(value) = adw_tab_bar_set_inverted(adwTabBarPointer, value.gboolean)

    val isOverflowing: Boolean get() = adw_tab_bar_get_is_overflowing(adwTabBarPointer).boolean

    var startActionWidget: Widget?
        get() = adw_tab_bar_get_start_action_widget(adwTabBarPointer)?.asWidget()
        set(value) = adw_tab_bar_set_start_action_widget(adwTabBarPointer, value?.gtkWidgetPointer)

    val tabsRevealed: Boolean get() = adw_tab_bar_get_tabs_revealed(adwTabBarPointer).boolean

    var view: TabView?
        get() = adw_tab_bar_get_view(adwTabBarPointer)?.asTabView()
        set(value) = adw_tab_bar_set_view(adwTabBarPointer, value?.adwTabViewPointer)

    // TODO setupExtraDropTarget once there is GdkDragAction support
    // TODO extra-drag-drop signal

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_TAB_BAR, ::TabBar)
    }
}

