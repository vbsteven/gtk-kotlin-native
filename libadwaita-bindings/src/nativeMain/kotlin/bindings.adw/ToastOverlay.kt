package bindings.adw

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*

class ToastOverlay : Widget {

    val adwToastOverlayPointer get() = gtkWidgetPointer.asTypedPointer<AdwToastOverlay>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_toast_overlay_new()!!)

    var child: Widget?
        get() = adw_toast_overlay_get_child(adwToastOverlayPointer)?.asWidget()
        set(value) = adw_toast_overlay_set_child(adwToastOverlayPointer, value?.gtkWidgetPointer)

    fun addToast(toast: Toast) = adw_toast_overlay_add_toast(adwToastOverlayPointer, toast.adwToastPointer)

    companion object : ObjectCompanion<ToastOverlay>(toastOverlayTypeInfo)
}

private val toastOverlayTypeInfo = BuiltinTypeInfo(
    "AdwToastOverlay",
    ADW_TYPE_TOAST_OVERLAY,
    -1,
    -1,
    ::ToastOverlay
)