package bindings.gtk

import bindings.gio.ListModel
import bindings.gio.asListModel
import bindings.gobject.*
import kotlinx.cinterop.CPointer
import native.gtk.*

class SingleSelection : Object, SelectionModel {

    val gtkSingleSelectionPointer get() = gPointer.asTypedPointer<GtkSingleSelection>()
    override val gtkSelectionModelPointer get() = gPointer.asTypedPointer<GtkSelectionModel>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(model: ListModel) : this(gtk_single_selection_new(model.gListModelPointer)!!)

    var autoselect: Boolean
        get() = gtk_single_selection_get_autoselect(gtkSingleSelectionPointer).boolean
        set(value) = gtk_single_selection_set_autoselect(gtkSingleSelectionPointer, value.gboolean)

    var canUnselect: Boolean
        get() = gtk_single_selection_get_can_unselect(gtkSingleSelectionPointer).boolean
        set(value) = gtk_single_selection_set_can_unselect(gtkSingleSelectionPointer, value.gboolean)

    var model: ListModel?
        get() = gtk_single_selection_get_model(gtkSingleSelectionPointer)?.asListModel()
        set(value) = gtk_single_selection_set_model(gtkSingleSelectionPointer, value?.gListModelPointer)

    // TODO this may return GTK_INVALID_LIST_POSITION, make the result nullable?
    var selected: Int
        get() = gtk_single_selection_get_selected(gtkSingleSelectionPointer).toInt()
        set(value) = gtk_single_selection_set_selected(gtkSingleSelectionPointer, value.toUInt())

    val selectedItem: Object?
        get() = gtk_single_selection_get_selected_item(gtkSingleSelectionPointer)?.asObject()
}