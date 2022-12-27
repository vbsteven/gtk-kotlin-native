package bindings.adw

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.asWidget
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class TabPage : Object {
    val adwTabPagePointer get() = gPointer.asTypedPointer<AdwTabPage>()

    constructor(pointer: CPointer<*>) : super(pointer)

    val child: Widget
        get() = adw_tab_page_get_child(adwTabPagePointer)!!.asWidget()

    // TODO icon property with GIcon

    var indicatorActivatable: Boolean
        get() = adw_tab_page_get_indicator_activatable(adwTabPagePointer).boolean
        set(value) = adw_tab_page_set_indicator_activatable(adwTabPagePointer, value.gboolean)

    // TODO indicator icon propery with GIcon

    var indicatorTooltip: String
        get() = adw_tab_page_get_indicator_tooltip(adwTabPagePointer)?.toKString() ?: ""
        set(value) = adw_tab_page_set_indicator_tooltip(adwTabPagePointer, value)

    var loading: Boolean
        get() = adw_tab_page_get_loading(adwTabPagePointer).boolean
        set(value) = adw_tab_page_set_loading(adwTabPagePointer, value.gboolean)

    var needsAttention: Boolean
        get() = adw_tab_page_get_needs_attention(adwTabPagePointer).boolean
        set(value) = adw_tab_page_set_needs_attention(adwTabPagePointer, value.gboolean)

    val parent: TabPage?
        get() = adw_tab_page_get_parent(adwTabPagePointer)?.asTabPage()

    val pinned: Boolean
        get() = adw_tab_page_get_pinned(adwTabPagePointer).boolean

    val selected: Boolean
        get() = adw_tab_page_get_selected(adwTabPagePointer).boolean

    var title: String
        get() = adw_tab_page_get_title(adwTabPagePointer)?.toKString() ?: ""
        set(value) = adw_tab_page_set_title(adwTabPagePointer, value)

    var tooltip: String?
        get() = adw_tab_page_get_tooltip(adwTabPagePointer)?.toKString()
        set(value) = adw_tab_page_set_tooltip(adwTabPagePointer, value)






}