package bindings.adw

import bindings.adw.internal.staticStableRefDestroy
import bindings.glib.Variant
import bindings.glib.asVariant
import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.adwaita.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer

class Toast : Object {
    val adwToastPointer get() = gPointer.asTypedPointer<AdwToast>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(title: String) : this(adw_toast_new(title)!!)

    fun dismiss() = adw_toast_dismiss(adwToastPointer)

    var actionName: String?
        get() = adw_toast_get_action_name(adwToastPointer)?.toKString()
        set(value) = adw_toast_set_action_name(adwToastPointer, value)

    var actionTarget: Variant?
        get() = adw_toast_get_action_target_value(adwToastPointer)?.asVariant()
        set(value) = adw_toast_set_action_target_value(adwToastPointer, value?.variantPointer)

    var buttonLabel: String?
        get() = adw_toast_get_button_label(adwToastPointer)?.toKString()
        set(value) = adw_toast_set_button_label(adwToastPointer, value)

    var title: String
        get() = adw_toast_get_title(adwToastPointer)?.toKString() ?: ""
        set(value) = adw_toast_set_title(adwToastPointer, value)

    var customTitle: Widget?
        get() = adw_toast_get_custom_title(adwToastPointer)?.asWidget()
        set(value) = adw_toast_set_custom_title(adwToastPointer, value?.gtkWidgetPointer)

    var priority: AdwToastPriority
        get() = adw_toast_get_priority(adwToastPointer)
        set(value) = adw_toast_set_priority(adwToastPointer, value)

    var timeout: Int
        get() = adw_toast_get_timeout(adwToastPointer).toInt()
        set(value) = adw_toast_set_timeout(adwToastPointer, value.toUInt())

    var detailedActionName: String?
        get() = throw NotImplementedError("detailedActionName cannot be set")
        set(value) = adw_toast_set_detailed_action_name(adwToastPointer, value)

    fun onDismissed(func: (Toast) -> Unit) {
        g_signal_connect_data(
            adwToastPointer,
            "dismissed",
            staticAdwToastCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    fun onButtonClicked(func: (Toast) -> Unit) {
        g_signal_connect_data(
            adwToastPointer,
            "button-clicked",
            staticAdwToastCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object : ObjectCompanion<Toast>(toastTypeInfo)
}

private val toastTypeInfo = BuiltinTypeInfo(
    "AdwToast",
    ADW_TYPE_TOAST,
    -1,
    -1,
    ::Toast
)

private val staticAdwToastCallbackFunc: GCallback =
    staticCFunction { toastPointer: CPointer<AdwToast>,
                      data: gpointer ->
        data.asStableRef<(Toast) -> Unit>()
            .get()
            .invoke(toastPointer.asToast())
    }.reinterpret()
