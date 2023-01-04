package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gtk.*

open class EntryBuffer : Object {

    val gtkEntryBufferPointer get() = gPointer.asTypedPointer<GtkEntryBuffer>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(initialText: String? = null) : this(gtk_entry_buffer_new(initialText, initialText?.length ?: -1)!!)

    var text: String
        get() = gtk_entry_buffer_get_text(gtkEntryBufferPointer)?.toKString() ?: ""
        set(value) = gtk_entry_buffer_set_text(gtkEntryBufferPointer, value, value.length)

    var maxLength: Int
        get() = gtk_entry_buffer_get_max_length(gtkEntryBufferPointer)
        set(value) = gtk_entry_buffer_set_max_length(gtkEntryBufferPointer, value)

    fun insertText(position: Int, text: String, count: Int = text.length) =
        gtk_entry_buffer_insert_text(gtkEntryBufferPointer, position.toUInt(), text, count).toInt()

    fun deleteText(position: Int, count: Int) =
        gtk_entry_buffer_delete_text(gtkEntryBufferPointer, position.toUInt(), count).toInt()

    fun emitDeletedText(position: Int, count: Int) =
        gtk_entry_buffer_emit_deleted_text(gtkEntryBufferPointer, position.toUInt(), count.toUInt())

    fun emitInsertedText(position: Int, text: String, count: Int) =
        gtk_entry_buffer_emit_inserted_text(gtkEntryBufferPointer, position.toUInt(), text, count.toUInt())

    val bytes: Long
        get() = gtk_entry_buffer_get_bytes(gtkEntryBufferPointer).toLong()

    val length: Int
        get() = gtk_entry_buffer_get_length(gtkEntryBufferPointer).toInt()

    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_ENTRY_BUFFER,
            sizeOf<GtkEntryBufferClass>(),
            sizeOf<GtkEntryBuffer>(),
            ::EntryBuffer
        )
    }
}