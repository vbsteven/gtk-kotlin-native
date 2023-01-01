package bindings.adw

import bindings.adw.internal.staticStableRefDestroy
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.Window
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.adwaita.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gchararray
import native.gobject.gpointer

open class MessageDialog : Window {
    val adwMessageDialogPointer get() = gtkWindowPointer.asTypedPointer<AdwMessageDialog>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(
        parent: Window?,
        heading: String?,
        body: String?
    ) : this(adw_message_dialog_new(parent?.gtkWindowPointer, heading, body)!!)


    fun addResponse(response: String, label: String) {
        adw_message_dialog_add_response(adwMessageDialogPointer, response, label)
    }

    fun addResponse(response: String, label: String, appearance: AdwResponseAppearance) {
        adw_message_dialog_add_response(adwMessageDialogPointer, response, label)
        setResponseAppearance(response, appearance)
    }

    fun setResponseAppearance(response: String, appearance: AdwResponseAppearance) {
        adw_message_dialog_set_response_appearance(adwMessageDialogPointer, response, appearance)
    }

    fun getResponseAppearance(response: String): AdwResponseAppearance {
        return adw_message_dialog_get_response_appearance(adwMessageDialogPointer, response)
    }

    fun isResponseEnabled(response: String): Boolean =
        adw_message_dialog_get_response_enabled(adwMessageDialogPointer, response).boolean

    fun getResponseLabel(response: String): String =
        adw_message_dialog_get_response_label(adwMessageDialogPointer, response)?.toKString() ?: ""

    fun setResponseLabel(response: String, label: String) =
        adw_message_dialog_set_response_label(adwMessageDialogPointer, response, label)

    fun hasResponse(response: String): Boolean =
        adw_message_dialog_has_response(adwMessageDialogPointer, response).boolean


    /**
     * Emit the response signal with the given response id
     */
    fun response(response: String) = adw_message_dialog_response(adwMessageDialogPointer, response)

    // TODO support the various format methods?

    var body: String
        get() = adw_message_dialog_get_body(adwMessageDialogPointer)?.toKString() ?: ""
        set(value) = adw_message_dialog_set_body(adwMessageDialogPointer, value)

    var bodyUseMarkup: Boolean
        get() = adw_message_dialog_get_body_use_markup(adwMessageDialogPointer).boolean
        set(value) = adw_message_dialog_set_body_use_markup(adwMessageDialogPointer, value.gboolean)

    var closeResponse: String
        get() = adw_message_dialog_get_close_response(adwMessageDialogPointer)?.toKString() ?: ""
        set(value) = adw_message_dialog_set_close_response(adwMessageDialogPointer, value)

    var heading: String?
        get() = adw_message_dialog_get_heading(adwMessageDialogPointer)?.toKString()
        set(value) = adw_message_dialog_set_heading(adwMessageDialogPointer, value)

    var headingUseMarkup: Boolean
        get() = adw_message_dialog_get_heading_use_markup(adwMessageDialogPointer).boolean
        set(value) = adw_message_dialog_set_heading_use_markup(adwMessageDialogPointer, value.gboolean)

    var defaultResponse: String
        get() = adw_message_dialog_get_default_response(adwMessageDialogPointer)?.toKString() ?: ""
        set(value) = adw_message_dialog_set_default_response(adwMessageDialogPointer, value)

    var extraChild: Widget?
        get() = adw_message_dialog_get_extra_child(adwMessageDialogPointer)?.asWidget()
        set(value) = adw_message_dialog_set_extra_child(adwMessageDialogPointer, value?.gtkWidgetPointer)


    /**
     * Connect the given [func] the response signal.
     * The argument passed to [func] is the response id.
     */
    fun onResponse(func: (String) -> Unit) {
        g_signal_connect_data(
            adwMessageDialogPointer,
            "response",
            staticAdwMessageDialogResponseCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object {
        val Type = BuiltinTypeInfo(
            ADW_TYPE_MESSAGE_DIALOG,
            sizeOf<AdwMessageDialogClass>(),
            sizeOf<AdwMessageDialog>(),
            ::MessageDialog
        )
    }
}


private val staticAdwMessageDialogResponseCallbackFunc: GCallback =
    staticCFunction { widgetPointer: AdwMessageDialog_autoptr,
                      response: gchararray,
                      data: gpointer ->
        data.asStableRef<(String) -> Unit>().get().invoke(response.toKString())
    }.reinterpret()
