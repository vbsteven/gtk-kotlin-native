package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.adwaita.*

open class PreferencesWindow : Window {
    val adwPreferencesWindowPointer get() = adwWindowPointer.asTypedPointer<AdwPreferencesWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_preferences_window_new()!!)

    var canNavigateBack: Boolean
        get() = adw_preferences_window_get_can_navigate_back(adwPreferencesWindowPointer).boolean
        set(value) = adw_preferences_window_set_can_navigate_back(adwPreferencesWindowPointer, value.gboolean)

    var searchEnabled: Boolean
        get() = adw_preferences_window_get_search_enabled(adwPreferencesWindowPointer).boolean
        set(value) = adw_preferences_window_set_search_enabled(adwPreferencesWindowPointer, value.gboolean)

    var visiblePageName: String?
        get() = adw_preferences_window_get_visible_page_name(adwPreferencesWindowPointer)?.toKString()
        set(value) = adw_preferences_window_set_visible_page_name(adwPreferencesWindowPointer, value)

    var visiblePage: PreferencesPage?
        get() = adw_preferences_window_get_visible_page(adwPreferencesWindowPointer)?.asPreferencesPage()
        set(value) = adw_preferences_window_set_visible_page(
            adwPreferencesWindowPointer,
            value?.adwPreferencesPagePointer
        )

    fun add(page: PreferencesPage) {
        adw_preferences_window_add(adwPreferencesWindowPointer, page.adwPreferencesPagePointer)
    }

    fun remove(page: PreferencesPage) {
        adw_preferences_window_remove(adwPreferencesWindowPointer, page.adwPreferencesPagePointer)
    }

    fun addToast(toast: Toast) {
        adw_preferences_window_add_toast(adwPreferencesWindowPointer, toast.adwToastPointer)
    }

    fun presentSubpage(subpage: Widget) {
        adw_preferences_window_present_subpage(adwPreferencesWindowPointer, subpage.gtkWidgetPointer)
    }

    fun closeSubpage() {
        adw_preferences_window_close_subpage(adwPreferencesWindowPointer)
    }

    companion object {
        val Type = BuiltinTypeInfo(
            "AdwPreferencesWindow",
            ADW_TYPE_PREFERENCES_WINDOW,
            sizeOf<AdwPreferencesWindowClass>(),
            sizeOf<AdwPreferencesWindow>(),
            ::PreferencesWindow
        )
    }

}
