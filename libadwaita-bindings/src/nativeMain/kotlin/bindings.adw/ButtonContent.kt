package bindings.adw

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class ButtonContent : Widget {
    val adwButtonContentPointer get() = gtkWidgetPointer.asTypedPointer<AdwButtonContent>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_button_content_new()!!)
    constructor(label: String = "", iconName: String = "", useUnderline: Boolean = true) : this() { // TODO what is the default for useUnderline?
        this.label = label
        this.iconName = iconName
        this.useUnderline = true
    }

    var iconName: String?
        get() = adw_button_content_get_icon_name(adwButtonContentPointer)?.toKString()
        set(value) = adw_button_content_set_icon_name(adwButtonContentPointer, value)

    var label: String?
        get() = adw_button_content_get_label(adwButtonContentPointer)?.toKString()
        set(value) = adw_button_content_set_label(adwButtonContentPointer, value)

    var useUnderline: Boolean
        get() = adw_button_content_get_use_underline(adwButtonContentPointer).boolean
        set(value) = adw_button_content_set_use_underline(adwButtonContentPointer, value.gboolean)

    companion object : ObjectCompanion<ButtonContent>(buttonContentTypeInfo)
}

private val buttonContentTypeInfo = BuiltinTypeInfo(
    "AdwButtonContent",
    ADW_TYPE_BUTTON_CONTENT,
    -1,
    -1,
    ::ButtonContent
)