package bindings.gtk

import bindings.gio.ListModel
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gio.GListModel
import native.gtk.GtkSelectionModel

interface SelectionModel : ListModel {
    val gtkSelectionModelPointer: CPointer<GtkSelectionModel>
    override val gListModelPointer get() = gtkSelectionModelPointer.asTypedPointer<GListModel>()

    // TODO implement the rest of the interface
}