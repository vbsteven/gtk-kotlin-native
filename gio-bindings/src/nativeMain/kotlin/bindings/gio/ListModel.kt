package bindings.gio

import kotlinx.cinterop.CPointer
import native.gio.GListModel
import native.gio.g_list_model_get_n_items
import native.gio.g_list_model_get_object
import native.gio.g_list_model_get_type
import native.gobject.GType
import native.gobject.gpointer

interface ListModel {
    val gListModelPointer: CPointer<GListModel>

    val itemCount: UInt
        get() = g_list_model_get_n_items(gListModelPointer)

    val itemType: GType
        get() = g_list_model_get_type()

    fun getObject(position: UInt): gpointer? {
        return g_list_model_get_object(gListModelPointer, position)
    }

    fun getObject(position: Int) = getObject(position.toUInt())
}

fun CPointer<GListModel>.asListModel(): ListModel = ListModelWrapper(this)

private class ListModelWrapper(pointer: CPointer<*>) : ListModel {
    override val gListModelPointer = pointer as CPointer<GListModel>
}