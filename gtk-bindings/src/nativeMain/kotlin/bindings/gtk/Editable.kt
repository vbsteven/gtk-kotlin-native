package bindings.gtk

import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.internal.staticStableRefDestroy
import kotlinx.cinterop.*
import native.gobject.GCallback
import native.gobject.g_free
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

interface Editable {
    val gtkEditablePointer: CPointer<GtkEditable>

    fun deleteSelection() = gtk_editable_delete_selection(gtkEditablePointer)
    fun deleteText(startPos: Int, endPos: Int) = gtk_editable_delete_text(gtkEditablePointer, startPos, endPos)
    fun finishDelegate() = gtk_editable_finish_delegate(gtkEditablePointer)

    var alignment: Float
        get() = gtk_editable_get_alignment(gtkEditablePointer)
        set(value) = gtk_editable_set_alignment(gtkEditablePointer, value)

    fun getChars(startPos: Int, endPos: Int): String {
        val chars = gtk_editable_get_chars(gtkEditablePointer, startPos, endPos)!!
        val result = chars.toKString()
        g_free(chars) // TODO verify if this is correct
        return result
    }

    fun getDelegate(): Editable? = gtk_editable_get_delegate(gtkEditablePointer)?.asEditable()

    var editable: Boolean
        get() = gtk_editable_get_editable(gtkEditablePointer).boolean
        set(value) = gtk_editable_set_editable(gtkEditablePointer, value.gboolean)

    var enableUndo: Boolean
        get() = gtk_editable_get_enable_undo(gtkEditablePointer).boolean
        set(value) = gtk_editable_set_enable_undo(gtkEditablePointer, value.gboolean)

    var maxWidthChars: Int
        get() = gtk_editable_get_max_width_chars(gtkEditablePointer)
        set(value) = gtk_editable_set_max_width_chars(gtkEditablePointer, value)

    var position: Int
        get() = gtk_editable_get_position(gtkEditablePointer)
        set(value) = gtk_editable_set_position(gtkEditablePointer, value)

    val selectionBounds: IntRange?
        get() = memScoped {
            val startPos = alloc<IntVar>()
            val endPos = alloc<IntVar>()

            val result = gtk_editable_get_selection_bounds(gtkEditablePointer, startPos.ptr, endPos.ptr).boolean
            // TODO verify if the returned endPos value is inclusive or exclusive
            return if (result) IntRange(startPos.value, endPos.value) else null
        }

    var text: String
        get() = gtk_editable_get_text(gtkEditablePointer)!!.toKString()
        set(value) = gtk_editable_set_text(gtkEditablePointer, value)

    var widthChars: Int
        get() = gtk_editable_get_width_chars(gtkEditablePointer)
        set(value) = gtk_editable_set_width_chars(gtkEditablePointer, value)

    fun initDelegate() = gtk_editable_init_delegate(gtkEditablePointer)

    /**
     * @return position after the newly inserted text
     */
    fun insertText(text: String, position: Int): Int = memScoped {
        val pos = alloc<IntVar>()
        pos.value = position
        gtk_editable_insert_text(gtkEditablePointer, text, -1, pos.ptr)
        return pos.value
    }

    fun selectRegion(startPos: Int, endPos: Int) = gtk_editable_select_region(gtkEditablePointer, startPos, endPos)
    fun selectRegion(range: IntRange) = selectRegion(range.first, range.last)

    // TODO add delete-text signal
    // TODO add insert-text signal

    /**
     * Connect [func] to be invoked when the changed signal is emitted
     */
    fun onChanged(func: () -> Unit) {
        g_signal_connect_data(
            gtkEditablePointer,
            "changed",
            staticEditableCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }
}


private val staticEditableCallbackFunc: GCallback =
    staticCFunction { _: CPointer<GtkEditable>,
                      data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
        Unit
    }.reinterpret()