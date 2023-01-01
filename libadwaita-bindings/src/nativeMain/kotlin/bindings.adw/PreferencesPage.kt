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

open class PreferencesPage : Widget {
    val adwPreferencesPagePointer get() = gtkWidgetPointer.asTypedPointer<AdwPreferencesPage>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_preferences_page_new()!!)

    var iconName: String?
        get() = adw_preferences_page_get_icon_name(adwPreferencesPagePointer)?.toKString()
        set(value) = adw_preferences_page_set_icon_name(adwPreferencesPagePointer, value)

    // TODO check this, widget already has a name property
    var name: String?
        get() = adw_preferences_page_get_name(adwPreferencesPagePointer)?.toKString()
        set(value) = adw_preferences_page_set_name(adwPreferencesPagePointer, value)

    var title: String
        get() = adw_preferences_page_get_title(adwPreferencesPagePointer)?.toKString() ?: ""
        set(value) = adw_preferences_page_set_title(adwPreferencesPagePointer, value)

    var useUnderline: Boolean
        get() = adw_preferences_page_get_use_underline(adwPreferencesPagePointer).boolean
        set(value) = adw_preferences_page_set_use_underline(adwPreferencesPagePointer, value.gboolean)

    fun add(group: PreferencesGroup) =
        adw_preferences_page_add(adwPreferencesPagePointer, group.adwPreferencesGroupPointer)

    fun remove(group: PreferencesGroup) =
        adw_preferences_page_remove(adwPreferencesPagePointer, group.adwPreferencesGroupPointer)

    companion object {
        val Type = BuiltinTypeInfo(
            ADW_TYPE_PREFERENCES_PAGE,
            sizeOf<AdwPreferencesPageClass>(),
            sizeOf<AdwPreferencesPage>(),
            ::PreferencesPage
        )
    }

}
