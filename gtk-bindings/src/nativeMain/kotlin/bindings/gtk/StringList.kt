package bindings.gtk

import bindings.gio.ListModel
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.util.toCStringList
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import native.gio.GListModel
import native.gtk.*

class StringList : Object, ListModel {

    val gtkStringListPointer get() = gPointer.asTypedPointer<GtkStringList>()
    override val gListModelPointer get() = gPointer.asTypedPointer<GListModel>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(items: List<String> = emptyList()) : this(createStringList(items))

    constructor(vararg items: String) : this(items.asList())

    fun append(item: String) = gtk_string_list_append(gtkStringListPointer, item)

    fun getString(position: Int) = gtk_string_list_get_string(gtkStringListPointer, position.toUInt())

    fun remove(position: Int) = gtk_string_list_remove(gtkStringListPointer, position.toUInt())

    // TODO add splice
    // TODO add take?
}

private fun createStringList(items: List<String>): CPointer<GtkStringList> = memScoped {
    val s = items.toCStringList(this)
    return gtk_string_list_new(s)!!
}