package bindings.gio

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gio.*
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

fun CPointer<GListModel>.asListModel(): ListModel = ListModelWrapper(this)

private class ListModelWrapper(pointer: CPointer<*>) : ListModel {
    override val gListModelPointer = pointer.asTypedPointer<GListModel>()
}