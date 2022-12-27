package bindings.gio

import kotlinx.cinterop.CPointer
import native.gio.GListModel
import native.gio.g_list_model_get_item_type
import native.gio.g_list_model_get_n_items
import native.gio.g_list_model_get_object
import native.gobject.GType
import native.gobject.gpointer

interface ListModel {
    val gListModelPointer: CPointer<GListModel>

    val itemCount: Int
        get() = g_list_model_get_n_items(gListModelPointer).toInt()

    val itemType: GType
        get() = g_list_model_get_item_type(gListModelPointer)

    fun getObject(position: UInt): gpointer? {
        return g_list_model_get_object(gListModelPointer, position)
    }

    fun getObject(position: Int) = getObject(position.toUInt())
}
