package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Actionable
import bindings.gtk.ListBoxRow
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.adwaita.*
import native.gtk.GtkActionable

open class PreferencesRow : ListBoxRow, Actionable {

    val adwPreferencesRowPointer get() = gtkListBoxRowPointer.asTypedPointer<AdwPreferencesRow>()

    override val gtkActionablePointer get() = adwPreferencesRowPointer.asTypedPointer<GtkActionable>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_preferences_row_new()!!)

    var title: String
        get() = adw_preferences_row_get_title(adwPreferencesRowPointer)!!.toKString()
        set(value) = adw_preferences_row_set_title(adwPreferencesRowPointer, value)

    var useUnderline: Boolean
        get() = adw_preferences_row_get_use_underline(adwPreferencesRowPointer).boolean
        set(value) = adw_preferences_row_set_use_underline(adwPreferencesRowPointer, value.gboolean)

    var useMarkup: Boolean
        get() = adw_preferences_row_get_use_markup(adwPreferencesRowPointer).boolean
        set(value) = adw_preferences_row_set_use_markup(adwPreferencesRowPointer, value.gboolean)

    var titleSelectable: Boolean
        get() = adw_preferences_row_get_title_selectable(adwPreferencesRowPointer).boolean
        set(value) = adw_preferences_row_set_title_selectable(adwPreferencesRowPointer, value.gboolean)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "AdwPreferencesRow",
            ADW_TYPE_PREFERENCES_ROW,
            sizeOf<AdwPreferencesRowClass>(),
            sizeOf<AdwPreferencesRow>(),
            ::PreferencesRow
        )
    }
}
