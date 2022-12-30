package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gio.*
import native.gobject.GType

class ListStore : Object, ListModel {
    override val gListModelPointer get() = gPointer.asTypedPointer<GListModel>()

    val gListStorePointer get() = gPointer.asTypedPointer<GListStore>()

    constructor(itemType: GType) : super(g_list_store_new(itemType)!!)
    constructor(pointer: CPointer<*>) : super(pointer)


    fun append(item: Object) = g_list_store_append(gListStorePointer, item.gPointer)
    fun appendAll(vararg items: Object) = items.forEach { append(it) }
    fun appendAll(items: List<Object>) = items.forEach { append(it) }

    fun insert(position: Int, item: Object) = g_list_store_insert(gListStorePointer, position.toUInt(), item.gPointer)
    fun remove(position: Int) = g_list_store_remove(gListStorePointer, position.toUInt())
    fun removeAll() = g_list_store_remove_all(gListStorePointer)

    // TODO insert_sorted
    // TODO find
    // TODO find_with_equal_func
    // TODO find_with_equal_func_full
    // TODO sort
    // TODO splice

}