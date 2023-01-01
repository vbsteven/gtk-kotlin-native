package bindings.adw

import bindings.adw.internal.staticStableRefDestroy
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Editable
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.adwaita.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.GtkEditable
import native.gtk.GtkInputHints
import native.gtk.GtkInputPurpose

open class EntryRow : PreferencesRow, Editable {
    val adwEntryRowPointer get() = gtkWidgetPointer.asTypedPointer<AdwEntryRow>()

    override var gtkEditablePointer = adwEntryRowPointer.asTypedPointer<GtkEditable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_entry_row_new()!!)

    fun addPrefix(widget: Widget) = adw_entry_row_add_prefix(adwEntryRowPointer, widget.gtkWidgetPointer)

    fun addSuffix(widget: Widget) = adw_entry_row_add_suffix(adwEntryRowPointer, widget.gtkWidgetPointer)

    fun remove(widget: Widget) = adw_entry_row_remove(adwEntryRowPointer, widget.gtkWidgetPointer)

    var activatesDefault: Boolean
        get() = adw_entry_row_get_activates_default(adwEntryRowPointer).boolean
        set(value) = adw_entry_row_set_activates_default(adwEntryRowPointer, value.gboolean)

    // TODO attributes property with PangoAttrList

    var enableEmojiCompletion: Boolean
        get() = adw_entry_row_get_enable_emoji_completion(adwEntryRowPointer).boolean
        set(value) = adw_entry_row_set_enable_emoji_completion(adwEntryRowPointer, value.gboolean)


    var inputHints: GtkInputHints
        get() = adw_entry_row_get_input_hints(adwEntryRowPointer)
        set(value) = adw_entry_row_set_input_hints(adwEntryRowPointer, value)

    var inputPurpose: GtkInputPurpose
        get() = adw_entry_row_get_input_purpose(adwEntryRowPointer)
        set(value) = adw_entry_row_set_input_purpose(adwEntryRowPointer, value)

    var showApplyButton: Boolean
        get() = adw_entry_row_get_show_apply_button(adwEntryRowPointer).boolean
        set(value) = adw_entry_row_set_show_apply_button(adwEntryRowPointer, value.gboolean)

    /**
     * Connect [func] to be invoked when the apply signal is emitted.
     */
    fun onApply(func: () -> Unit) {
        g_signal_connect_data(
            adwEntryRowPointer,
            "apply",
            staticEntryRowCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    fun onEntryActivated(func: () -> Unit) {
        g_signal_connect_data(
            adwEntryRowPointer,
            "entry-activated",
            staticEntryRowCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    companion object {
        val Type = BuiltinTypeInfo(
            ADW_TYPE_ENTRY_ROW,
            sizeOf<AdwEntryRowClass>(),
            sizeOf<AdwEntryRow>(),
            ::EntryRow
        )
    }
}

private val staticEntryRowCallbackFunc: GCallback =
    staticCFunction { _: CPointer<AdwEntryRow>,
                      data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
        Unit
    }.reinterpret()