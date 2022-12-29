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

open class ExpanderRow : PreferencesRow {

    val adwExpanderRowPointer get() = adwPreferencesRowPointer.asTypedPointer<AdwExpanderRow>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_expander_row_new()!!)


    var expanded: Boolean
        get() = adw_expander_row_get_expanded(adwExpanderRowPointer).boolean
        set(value) = adw_expander_row_set_expanded(adwExpanderRowPointer, value.gboolean)

    var iconName: String?
        get() = adw_expander_row_get_icon_name(adwExpanderRowPointer)?.toKString()
        set(value) = adw_expander_row_set_icon_name(adwExpanderRowPointer, value)

    var showEnableSwitch: Boolean
        get() = adw_expander_row_get_show_enable_switch(adwExpanderRowPointer).boolean
        set(value) = adw_expander_row_set_show_enable_switch(adwExpanderRowPointer, value.gboolean)

    var enableExpansion: Boolean
        get() = adw_expander_row_get_enable_expansion(adwExpanderRowPointer).boolean
        set(value) = adw_expander_row_set_enable_expansion(adwExpanderRowPointer, value.gboolean)

    var subtitle: String
        get() = adw_expander_row_get_subtitle(adwExpanderRowPointer)!!.toKString()
        set(value) = adw_expander_row_set_subtitle(adwExpanderRowPointer, value)

    fun addPrefix(widget: Widget) = adw_expander_row_add_prefix(adwExpanderRowPointer, widget.gtkWidgetPointer)

    fun addAction(widget: Widget) = adw_expander_row_add_action(adwExpanderRowPointer, widget.gtkWidgetPointer)

    fun addRow(widget: Widget) = adw_expander_row_add_row(adwExpanderRowPointer, widget.gtkWidgetPointer)

    fun remove(widget: Widget) = adw_expander_row_remove(adwExpanderRowPointer, widget.gtkWidgetPointer)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "AdwExpanderRow",
            ADW_TYPE_EXPANDER_ROW,
            sizeOf<AdwExpanderRowClass>(),
            sizeOf<AdwExpanderRow>(),
            ::ExpanderRow
        )
    }
}