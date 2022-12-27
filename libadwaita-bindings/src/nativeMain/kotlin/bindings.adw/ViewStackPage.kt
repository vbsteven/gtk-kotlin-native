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

class ViewStackPage : Object {

    val adwViewStackPagePointer get() = gPointer.asTypedPointer<AdwViewStackPage>()

    constructor(pointer: CPointer<*>) : super(pointer)

    var badgeNumber: Int
        get() = adw_view_stack_page_get_badge_number(adwViewStackPagePointer).toInt()
        set(value) = adw_view_stack_page_set_badge_number(adwViewStackPagePointer, value.toUInt())

    val child: Widget
        get() = adw_view_stack_page_get_child(adwViewStackPagePointer)!!.asWidget()

    var iconName: String?
        get() = adw_view_stack_page_get_icon_name(adwViewStackPagePointer)?.toKString()
        set(value) = adw_view_stack_page_set_icon_name(adwViewStackPagePointer, value)

    var name: String?
        get() = adw_view_stack_page_get_name(adwViewStackPagePointer)?.toKString()
        set(value) = adw_view_stack_page_set_name(adwViewStackPagePointer, value)

    var needsAttention: Boolean
        get() = adw_view_stack_page_get_needs_attention(adwViewStackPagePointer).boolean
        set(value) = adw_view_stack_page_set_needs_attention(adwViewStackPagePointer, value.gboolean)

    var title: String?
        get() = adw_view_stack_page_get_title(adwViewStackPagePointer)?.toKString()
        set(value) = adw_view_stack_page_set_title(adwViewStackPagePointer, value)

    var useUnderline: Boolean
        get() = adw_view_stack_page_get_use_underline(adwViewStackPagePointer).boolean
        set(value) = adw_view_stack_page_set_use_underline(adwViewStackPagePointer, value.gboolean)

    var visible: Boolean
        get() = adw_view_stack_page_get_visible(adwViewStackPagePointer).boolean
        set(value) = adw_view_stack_page_set_visible(adwViewStackPagePointer, value.gboolean)
}