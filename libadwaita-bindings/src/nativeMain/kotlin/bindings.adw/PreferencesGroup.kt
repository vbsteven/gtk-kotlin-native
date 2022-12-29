package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.adwaita.*

open class PreferencesGroup : Widget {
    val adwPreferencesGroupPointer get() = gtkWidgetPointer.asTypedPointer<AdwPreferencesGroup>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_preferences_group_new()!!)

    var title: String
        get() = adw_preferences_group_get_title(adwPreferencesGroupPointer)!!.toKString()
        set(value) = adw_preferences_group_set_title(adwPreferencesGroupPointer, value)

    var description: String?
        get() = adw_preferences_group_get_description(adwPreferencesGroupPointer)?.toKString()
        set(value) = adw_preferences_group_set_description(adwPreferencesGroupPointer, value)

    var headerSuffix: Widget?
        get() = adw_preferences_group_get_header_suffix(adwPreferencesGroupPointer)?.asWidget()
        set(value) = adw_preferences_group_set_header_suffix(adwPreferencesGroupPointer, value?.gtkWidgetPointer)

    fun add(widget: Widget) = adw_preferences_group_add(adwPreferencesGroupPointer, widget.gtkWidgetPointer)

    fun remove(widget: Widget) = adw_preferences_group_remove(adwPreferencesGroupPointer, widget.gtkWidgetPointer)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "AdwPreferencesGroup",
            ADW_TYPE_PREFERENCES_GROUP,
            sizeOf<AdwPreferencesGroupClass>(),
            sizeOf<AdwPreferencesGroup>(),
            ::PreferencesGroup
        )
    }

}