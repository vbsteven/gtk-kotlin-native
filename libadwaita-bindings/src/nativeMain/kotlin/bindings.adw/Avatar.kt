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

class Avatar : Widget {

    val adwAvatarPointer get() = gtkWidgetPointer.asTypedPointer<AdwAvatar>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(
        size: Int,
        text: String?,
        showInitials: Boolean,
    ) : this(adw_avatar_new(size, text, showInitials.gboolean)!!)

    var iconName: String?
        get() = adw_avatar_get_icon_name(adwAvatarPointer)?.toKString()
        set(value) = adw_avatar_set_icon_name(adwAvatarPointer, value)

    var showInitials: Boolean
        get() = adw_avatar_get_show_initials(adwAvatarPointer).boolean
        set(value) = adw_avatar_set_show_initials(adwAvatarPointer, value.gboolean)

    var size: Int
        get() = adw_avatar_get_size(adwAvatarPointer)
        set(value) = adw_avatar_set_size(adwAvatarPointer, value)

    var text: String?
        get() = adw_avatar_get_text(adwAvatarPointer)?.toKString()
        set(value) = adw_avatar_set_text(adwAvatarPointer, value)

    companion object : ObjectCompanion<Avatar>(avatarTypeInfo)
}

private val avatarTypeInfo = BuiltinTypeInfo(
    "AdwAvatar",
    ADW_TYPE_AVATAR,
    -1,
    -1,
    ::Avatar
)