package bindings.gtk

import bindings.gio.ListModel
import bindings.gio.asListModel
import bindings.gobject.*
import kotlinx.cinterop.CPointer
import native.gtk.*

class TreeListRow : Object {
    val gtkTreeListRowPointer get() = gPointer.asTypedPointer<GtkTreeListRow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    fun getChildRow(position: Int): TreeListRow? =
        gtk_tree_list_row_get_child_row(gtkTreeListRowPointer, position.toUInt())?.asTreeListRow()

    val children: ListModel? get() = gtk_tree_list_row_get_children(gtkTreeListRowPointer)?.asListModel()

    val depth: Int get() = gtk_tree_list_row_get_depth(gtkTreeListRowPointer).toInt()

    var expanded: Boolean
        get() = gtk_tree_list_row_get_expanded(gtkTreeListRowPointer).boolean
        set(value) = gtk_tree_list_row_set_expanded(gtkTreeListRowPointer, value.gboolean)

    val item: Object? get() = gtk_tree_list_row_get_item(gtkTreeListRowPointer)?.asObject()

}