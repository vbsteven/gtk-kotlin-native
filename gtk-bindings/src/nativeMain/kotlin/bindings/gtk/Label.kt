package bindings.gtk

import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gtk.*

class Label(pointer: CPointer<*>) : Widget(pointer) {

    val gtkLabelPointer get() = gtkWidgetPointer.asTypedPointer<GtkLabel>()

    /**
     * Creates a new [Label] with the given [text] inside it.
     *
     * If [withMnemonic] is true, characters in [text] that are preceded by an underscore are underlined.
     *
     * @param text the label text
     * @param withMnemonic true
     */
    constructor(text: String? = null, withMnemonic: Boolean = false) : this(newPointer(text, withMnemonic))

    var text: String
        get() = gtk_label_get_text(gtkLabelPointer)?.toKString() ?: ""
        set(value) = gtk_label_set_text(gtkLabelPointer, value)

    val currentUri: String?
        get() = gtk_label_get_current_uri(gtkLabelPointer)?.toKString()

    var ellipsize: PangoEllipsizeMode
        get() = gtk_label_get_ellipsize(gtkLabelPointer)
        set(value) = gtk_label_set_ellipsize(gtkLabelPointer, value)

    var extraMenu: MenuModel?
        get() = gtk_label_get_extra_menu(gtkLabelPointer)?.asMenuModel()
        set(value) = gtk_label_set_extra_menu(gtkLabelPointer, value?.gMenuModelPointer)

    var justify: GtkJustification
        get() = gtk_label_get_justify(gtkLabelPointer)
        set(value) = gtk_label_set_justify(gtkLabelPointer, value)

    var label: String?
        get() = gtk_label_get_label(gtkLabelPointer)?.toString()
        set(value) = gtk_label_set_label(gtkLabelPointer, value)

    // TODO layout and layoutOffsets properties with PangoLayout

    var lines: Int
        get() = gtk_label_get_lines(gtkLabelPointer)
        set(value) = gtk_label_set_lines(gtkLabelPointer, value)

    var maxWidthChars: Int
        get() = gtk_label_get_max_width_chars(gtkLabelPointer)
        set(value) = gtk_label_set_max_width_chars(gtkLabelPointer, value)

    val mnemonicKeyval: Int
        get() = gtk_label_get_mnemonic_keyval(gtkLabelPointer).toInt()

    var mnemonicWidget: Widget?
        get() = gtk_label_get_mnemonic_widget(gtkLabelPointer)?.asWidget()
        set(value) = gtk_label_set_mnemonic_widget(gtkLabelPointer, value?.gtkWidgetPointer)

    var naturalWrapMode: GtkNaturalWrapMode
        get() = gtk_label_get_natural_wrap_mode(gtkLabelPointer)
        set(value) = gtk_label_set_natural_wrap_mode(gtkLabelPointer, value)

    var selectable: Boolean
        get() = gtk_label_get_selectable(gtkLabelPointer).boolean
        set(value) = gtk_label_set_selectable(gtkLabelPointer, value.gboolean)

    val selectionBounds: IntRange?
        get() = memScoped {
            val startPos = alloc<IntVar>()
            val endPos = alloc<IntVar>()

            val result = gtk_label_get_selection_bounds(gtkLabelPointer, startPos.ptr, endPos.ptr).boolean
            // TODO verify if the returned endPos value is inclusive or exclusive
            return if (result) IntRange(startPos.value, endPos.value) else null
        }

    var singleLineMode: Boolean
        get() = gtk_label_get_single_line_mode(gtkLabelPointer).boolean
        set(value) = gtk_label_set_single_line_mode(gtkLabelPointer, value.gboolean)

    // TODO tabs property with PangoTabArray

    var useMarkup: Boolean
        get() = gtk_label_get_use_markup(gtkLabelPointer).boolean
        set(value) = gtk_label_set_use_markup(gtkLabelPointer, value.gboolean)

    var useUnderlin: Boolean
        get() = gtk_label_get_use_underline(gtkLabelPointer).boolean
        set(value) = gtk_label_set_use_underline(gtkLabelPointer, value.gboolean)

    var widthChars: Int
        get() = gtk_label_get_width_chars(gtkLabelPointer)
        set(value) = gtk_label_set_width_chars(gtkLabelPointer, value)

    var wrap: Boolean
        get() = gtk_label_get_wrap(gtkLabelPointer).boolean
        set(value) = gtk_label_set_wrap(gtkLabelPointer, value.gboolean)

    var wrapMode: PangoWrapMode
        get() = gtk_label_get_wrap_mode(gtkLabelPointer)
        set(value) = gtk_label_set_wrap_mode(gtkLabelPointer, value)

    var xalign: Float
        get() = gtk_label_get_xalign(gtkLabelPointer)
        set(value) = gtk_label_set_xalign(gtkLabelPointer, value)

    var yalign: Float
        get() = gtk_label_get_yalign(gtkLabelPointer)
        set(value) = gtk_label_set_yalign(gtkLabelPointer, value)

    fun selectRegion(startOffset: Int, endOffset: Int) =
        gtk_label_select_region(gtkLabelPointer, startOffset, endOffset)

    fun setMarkup(str: String, withMnemonic: Boolean) {
        if (withMnemonic) gtk_label_set_markup_with_mnemonic(gtkLabelPointer, str)
        else gtk_label_set_markup(gtkLabelPointer, str)
    }

    fun setText(str: String, withMnemonic: Boolean) {
        if (withMnemonic) gtk_label_set_text_with_mnemonic(gtkLabelPointer, str)
        else gtk_label_set_text(gtkLabelPointer, str)
    }


    companion object {
        val Type = BuiltinTypeInfo<Label>("GtkLabel", GTK_TYPE_LABEL, -1, -1, ::Label)

        /* helpers for constructor */
        private fun newPointer(text: String?, withMnemonic: Boolean) =
            if (withMnemonic) newPointerWithMnemonic(text)
            else newPointerWithLabel(text)

        private fun newPointerWithLabel(text: String?) = gtk_label_new(text)!!
        private fun newPointerWithMnemonic(text: String?) = gtk_label_new_with_mnemonic(text)!!

        /**
         * // TODO can we make this generic for all final non-derivable wrapper classes?
         * @see [bindings.gobject.ObjectCompanion.fromObject]
         */
        fun fromObject(o: Object): Label {
            return Label(o.gPointer)
        }
    }
}