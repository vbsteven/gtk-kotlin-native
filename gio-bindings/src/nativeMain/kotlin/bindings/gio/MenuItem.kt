package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gio.*

class MenuItem : Object {

    val gMenuItemPointer get() = gPointer.asTypedPointer<GMenuItem>()

    constructor(label: String? = null, detailedAction: String? = null) : super(g_menu_item_new(label, detailedAction)!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    // TODO attribute property
    // TODO attributeValue property

    fun getLink(link: String): MenuModel? = g_menu_item_get_link(gMenuItemPointer, link)?.asMenuModel()
    fun setLink(link: String, model: MenuModel?) =
        g_menu_item_set_link(gMenuItemPointer, link, model?.gMenuModelPointer)

    // TODO setActionAndTarget with varargs?

    fun setDetailedAction(detailedAction: String) = g_menu_item_set_detailed_action(gMenuItemPointer, detailedAction)

    // TODO setIcon with GIcon

    fun setLabel(label: String?) = g_menu_item_set_label(gMenuItemPointer, label)

    fun setSection(section: MenuModel?) = g_menu_item_set_section(gMenuItemPointer, section?.gMenuModelPointer)
    fun setSubmenu(submenu: MenuModel?) = g_menu_item_set_submenu(gMenuItemPointer, submenu?.gMenuModelPointer)

    companion object {
        val Type = BuiltinTypeInfo(G_TYPE_MENU_ITEM, ::MenuItem)
    }
}