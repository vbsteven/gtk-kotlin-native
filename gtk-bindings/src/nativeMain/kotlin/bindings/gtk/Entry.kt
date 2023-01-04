package bindings.gtk

import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gtk.*

open class Entry : Widget, Editable {

    val gtkEntryPointer get() = gtkWidgetPointer.asTypedPointer<GtkEntry>()
    override val gtkEditablePointer get() = gtkWidgetPointer.asTypedPointer<GtkEditable>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(gtk_entry_new()!!)
    constructor(buffer: EntryBuffer) : this(gtk_entry_new_with_buffer(buffer.gtkEntryBufferPointer)!!)

    // TODO attributes property with PangoAttrList
    // TODO completion with GtkEntryCompletion
    // TODO icon area with GdkRectangle
    // TODO icon at pos
    // TODO icon_gicon
    // TODO invisible char
    // TODO tabs

    var activatesDefault: Boolean
        get() = gtk_entry_get_activates_default(gtkEntryPointer).boolean
        set(value) = gtk_entry_set_activates_default(gtkEntryPointer, value.gboolean)

    override var alignment: Float
        get() = gtk_entry_get_alignment(gtkEntryPointer)
        set(value) = gtk_entry_set_alignment(gtkEntryPointer, value)

    val currentIconDragSource: Int = gtk_entry_get_current_icon_drag_source(gtkEntryPointer)

    var extraManu: MenuModel?
        get() = gtk_entry_get_extra_menu(gtkEntryPointer)?.asMenuModel()
        set(value) = gtk_entry_set_extra_menu(gtkEntryPointer, value?.gMenuModelPointer)

    var buffer: EntryBuffer
        get() = gtk_entry_get_buffer(gtkEntryPointer)!!.asEntryBuffer()
        set(value) = gtk_entry_set_buffer(gtkEntryPointer, value.gtkEntryBufferPointer)

    var hasFrame: Boolean
        get() = gtk_entry_get_has_frame(gtkEntryPointer).boolean
        set(value) = gtk_entry_set_has_frame(gtkEntryPointer, value.gboolean)

    fun getIconActivatable(iconPos: GtkEntryIconPosition): Boolean =
        gtk_entry_get_icon_activatable(gtkEntryPointer, iconPos).boolean

    fun setIconActivatable(iconPos: GtkEntryIconPosition, activatable: Boolean) =
        gtk_entry_set_icon_activatable(gtkEntryPointer, iconPos, activatable.gboolean)

    var visibility: Boolean
        get() = gtk_entry_get_visibility(gtkEntryPointer).boolean
        set(value) = gtk_entry_set_visibility(gtkEntryPointer, value.gboolean)

    fun getIconName(iconPos: GtkEntryIconPosition) = gtk_entry_get_icon_name(gtkEntryPointer, iconPos)?.toKString()
    fun setIconName(iconPos: GtkEntryIconPosition, iconName: String?) =
        gtk_entry_set_icon_from_icon_name(gtkEntryPointer, iconPos, iconName)

    var inputHints: GtkInputHints
        get() = gtk_entry_get_input_hints(gtkEntryPointer)
        set(value) = gtk_entry_set_input_hints(gtkEntryPointer, value)

    var inputPurpose: GtkInputPurpose
        get() = gtk_entry_get_input_purpose(gtkEntryPointer)
        set(value) = gtk_entry_set_input_purpose(gtkEntryPointer, value)

    var maxLength: Int
        get() = gtk_entry_get_max_length(gtkEntryPointer)
        set(value) = gtk_entry_set_max_length(gtkEntryPointer, value)

    var overwriteMode: Boolean
        get() = gtk_entry_get_overwrite_mode(gtkEntryPointer).boolean
        set(value) = gtk_entry_set_overwrite_mode(gtkEntryPointer, value.gboolean)

    var placeholderText: String?
        get() = gtk_entry_get_placeholder_text(gtkEntryPointer)?.toKString()
        set(value) = gtk_entry_set_placeholder_text(gtkEntryPointer, value)

    var progressFraction: Double
        get() = gtk_entry_get_progress_fraction(gtkEntryPointer)
        set(value) = gtk_entry_set_progress_fraction(gtkEntryPointer, value)

    var progressPulseStep: Double
        get() = gtk_entry_get_progress_pulse_step(gtkEntryPointer)
        set(value) = gtk_entry_set_progress_pulse_step(gtkEntryPointer, value)

    fun unsetInvisibleChar() = gtk_entry_unset_invisible_char(gtkEntryPointer)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_ENTRY, sizeOf<GtkEntryClass>(), sizeOf<GtkEntry>(), ::Entry)
    }
}