package bindings.gtk

import bindings.gobject.*
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class TreeExpander : Widget {
    val gtkTreeExpanderPointer get() = gtkWidgetPointer.asTypedPointer<GtkTreeExpander>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_tree_expander_new()!!)

    var child: Widget?
        get() = gtk_tree_expander_get_child(gtkTreeExpanderPointer)?.asWidget()
        set(value) = gtk_tree_expander_set_child(gtkTreeExpanderPointer, value?.gtkWidgetPointer)

    var indentForIcon: Boolean
        get() = gtk_tree_expander_get_indent_for_icon(gtkTreeExpanderPointer).boolean
        set(value) = gtk_tree_expander_set_indent_for_icon(gtkTreeExpanderPointer, value.gboolean)

    val item: Object? get() = gtk_tree_expander_get_item(gtkTreeExpanderPointer)?.asObject()

    var listRow: TreeListRow?
        get() = gtk_tree_expander_get_list_row(gtkTreeExpanderPointer)?.asTreeListRow()
        set(value) = gtk_tree_expander_set_list_row(gtkTreeExpanderPointer, value?.gtkTreeListRowPointer)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_TREE_EXPANDER, ::TreeExpander)
    }
}