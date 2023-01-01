package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gio.*

open class MenuModel : Object {

    val gMenuModelPointer get() = gPointer.asTypedPointer<GMenuModel>()

    constructor(pointer: CPointer<*>) : super(pointer)

    // TODO getItemAttribute
    // TODO getItemAttributeValue

    /**
     * The count of items in this model.
     */
    val itemCount: Int get() = g_menu_model_get_n_items(gMenuModelPointer)

    fun getItemLink(position: Int, link: String): MenuModel? =
        g_menu_model_get_item_link(gMenuModelPointer, position, link)?.asMenuModel()

    val isMutable: Boolean get() = g_menu_model_is_mutable(gMenuModelPointer).boolean

    fun itemsChanged(position: Int, removed: Int, added: Int) =
        g_menu_model_items_changed(gMenuModelPointer, position, removed, added)

    // TODO iterateItemAttributes
    // TODO iterateItemLinks


    companion object {
        val Type = BuiltinTypeInfo(
            "GMenuModel",
            G_TYPE_MENU_MODEL,
            sizeOf<GMenuModelClass>(),
            sizeOf<GMenuModel>(),
            ::MenuModel
        )
    }

}