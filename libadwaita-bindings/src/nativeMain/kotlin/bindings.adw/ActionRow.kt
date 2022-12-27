package bindings.adw

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.adwaita.*

class ActionRow : PreferencesRow {
    val adwActionRowPointer get() = adwPreferencesRowPointer.asTypedPointer<AdwActionRow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_action_row_new()!!)

    // TODO activate signal handler

    fun activate() = adw_action_row_activate(adwActionRowPointer)

    fun addPrefix(widget: Widget) = adw_action_row_add_prefix(adwActionRowPointer, widget.gtkWidgetPointer)

    fun addSuffix(widget: Widget) = adw_action_row_add_suffix(adwActionRowPointer, widget.gtkWidgetPointer)

    fun remove(widget: Widget) = adw_action_row_remove(adwActionRowPointer, widget.gtkWidgetPointer)


    var activatableWidget: Widget?
        get() = adw_action_row_get_activatable_widget(adwActionRowPointer)?.asWidget()
        set(value) = adw_action_row_set_activatable_widget(adwActionRowPointer, value?.gtkWidgetPointer)

    var iconName: String?
        get() = adw_action_row_get_icon_name(adwActionRowPointer)?.toKString()
        set(value) = adw_action_row_set_icon_name(adwActionRowPointer, value)

    var subtitle: String?
        get() = adw_action_row_get_subtitle(adwActionRowPointer)?.toKString()
        set(value) = adw_action_row_set_subtitle(adwActionRowPointer, value)

    var titleLines: Int
        get() = adw_action_row_get_title_lines(adwActionRowPointer)
        set(value) = adw_action_row_set_title_lines(adwActionRowPointer, value)

    var subtitleLines: Int
        get() = adw_action_row_get_subtitle_lines(adwActionRowPointer)
        set(value) = adw_action_row_set_subtitle_lines(adwActionRowPointer, value)

    companion object : ObjectCompanion<ActionRow>(ActionRowTypeInfo)
}

private val ActionRowTypeInfo = BuiltinTypeInfo(
    "AdwActionRow",
    ADW_TYPE_ACTION_ROW,
    sizeOf<AdwActionRowClass>(),
    sizeOf<AdwActionRow>(),
    ::ActionRow
)