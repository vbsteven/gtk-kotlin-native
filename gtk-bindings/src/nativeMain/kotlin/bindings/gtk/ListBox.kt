package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class ListBox : Widget {

    val gtkListBoxPointer get() = gPointer.asTypedPointer<GtkListBox>()

    constructor() : super(gtk_list_box_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var selectionMode: GtkSelectionMode
        get() = gtk_list_box_get_selection_mode(gtkListBoxPointer)
        set(value) = gtk_list_box_set_selection_mode(gtkListBoxPointer, value)

    var showSeparators: Boolean
        get() = gtk_list_box_get_show_separators(gtkListBoxPointer).boolean
        set(value) = gtk_list_box_set_show_separators(gtkListBoxPointer, value.gboolean)

    var activateOnSingleClick: Boolean
        get() = gtk_list_box_get_activate_on_single_click(gtkListBoxPointer).boolean
        set(value) = gtk_list_box_set_activate_on_single_click(gtkListBoxPointer, value.gboolean)

    // TODO add adjustment :: GtkAdjustment

    fun append(widget: Widget) = gtk_list_box_append(gtkListBoxPointer, widget.widgetPointer)
    fun appendAll(vararg widgets: Widget) = widgets.forEach { append(it) }

    fun prepend(widget: Widget) = gtk_list_box_prepend(gtkListBoxPointer, widget.widgetPointer)

    fun insert(widget: Widget, pos: Int) = gtk_list_box_insert(gtkListBoxPointer, widget.widgetPointer, pos)

    fun remove(widget: Widget) = gtk_list_box_remove(gtkListBoxPointer, widget.widgetPointer)

    fun selectAll() = gtk_list_box_select_all(gtkListBoxPointer)
    fun selectRow(row: ListBoxRow) = gtk_list_box_select_row(gtkListBoxPointer, row.gtkListBoxRowPointer)

    fun unselectAll() = gtk_list_box_unselect_all(gtkListBoxPointer)
    fun unselectRow(row: ListBoxRow) = gtk_list_box_unselect_row(gtkListBoxPointer, row.gtkListBoxRowPointer)

    fun rowAtIndex(pos: Int): ListBoxRow? =
        gtk_list_box_get_row_at_index(gtkListBoxPointer, pos)?.asListBoxRow()

    fun rowAtY(y: Int): ListBoxRow? = gtk_list_box_get_row_at_y(gtkListBoxPointer, y)?.asListBoxRow()

    fun selectedRow(): ListBoxRow? = gtk_list_box_get_selected_row(gtkListBoxPointer)?.asListBoxRow()

    companion object : ObjectCompanion<ListBox>(ListBoxTypeInfo)
}

val ListBoxTypeInfo = BuiltinTypeInfo(
    "GtkListBox",
    GTK_TYPE_LIST_BOX,
    -1,
    -1,
    ::ListBox
)

fun CPointer<GtkListBox>.asListBox(): ListBox = ListBox(this)
