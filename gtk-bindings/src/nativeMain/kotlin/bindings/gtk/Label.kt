package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
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
        get() = gtk_label_get_text(gPointer.reinterpret())?.toKString() ?: ""
        set(value) = gtk_label_set_text(gPointer.reinterpret(), value)

    companion object {

        /* helpers for constructor */
        private fun newPointer(text: String?, withMnemonic: Boolean) =
            if (withMnemonic) newPointerWithLabel(text)
            else newPointerWithMnemonic(text)

        private fun newPointerWithLabel(text: String?) = gtk_label_new(text)!!
        private fun newPointerWithMnemonic(text: String?) = gtk_label_new_with_mnemonic(text)!!
    }
}