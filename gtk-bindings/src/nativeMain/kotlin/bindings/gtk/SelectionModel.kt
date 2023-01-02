package bindings.gtk

import bindings.gio.ListModel
import bindings.gobject.asTypedPointer
import bindings.gtk.internal.staticStableRefDestroy
import kotlinx.cinterop.*
import native.gio.GListModel
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.GtkSelectionModel

interface SelectionModel : ListModel {
    val gtkSelectionModelPointer: CPointer<GtkSelectionModel>
    override val gListModelPointer get() = gtkSelectionModelPointer.asTypedPointer<GListModel>()

    fun onSelectionChanged(handler: () -> Unit) {
        g_signal_connect_data(
            gtkSelectionModelPointer, "selection-changed", staticSelectionChangedFunc,
            StableRef.create(handler).asCPointer(), staticStableRefDestroy, 0
        )
    }

    // TODO implement the rest of the interface
}

private val staticSelectionChangedFunc: GCallback =
    staticCFunction { _: CPointer<GtkSelectionModel>,
                      position: UInt,
                      nItems: UInt,
                      data: gpointer ->
        data.asStableRef<() -> Unit>().get().invoke()
    }.reinterpret()
