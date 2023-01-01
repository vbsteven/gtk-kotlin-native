package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class StatusPage : Widget {
    val adwStatusPagePointer get() = gtkWidgetPointer.asTypedPointer<AdwStatusPage>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_status_page_new()!!)

    var title: String?
        get() = adw_status_page_get_title(adwStatusPagePointer)?.toKString()
        set(value) = adw_status_page_set_title(adwStatusPagePointer, value)

    var iconName: String?
        get() = adw_status_page_get_icon_name(adwStatusPagePointer)?.toKString()
        set(value) = adw_status_page_set_icon_name(adwStatusPagePointer, value)

    var description: String?
        get() = adw_status_page_get_description(adwStatusPagePointer)?.toKString()
        set(value) = adw_status_page_set_description(adwStatusPagePointer, value)

    var child: Widget?
        get() = adw_status_page_get_child(adwStatusPagePointer)?.asWidget()
        set(value) = adw_status_page_set_child(adwStatusPagePointer, value?.gtkWidgetPointer)

    // TODO add paintable with GdkPaintable support

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_STATUS_PAGE, ::StatusPage)
    }
}