package bindings.gio

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gio.*

class Menu : MenuModel {

    val gMenuPointer get() = gPointer.asTypedPointer<GMenu>()

    constructor() : super(g_menu_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    fun append(label: String? = null, detailedAction: String? = null) =
        g_menu_append(gMenuPointer, label, detailedAction)

    fun appendItem(item: MenuItem) = g_menu_append_item(gMenuPointer, item.gMenuItemPointer)

    fun appendSection(label: String? = null, section: MenuModel) =
        g_menu_append_section(gMenuPointer, label, section.gMenuModelPointer)

    fun appendSubmenu(label: String? = null, submenu: MenuModel) =
        g_menu_append_submenu(gMenuPointer, label, submenu.gMenuModelPointer)

    fun freeze() = g_menu_freeze(gMenuPointer)

    fun insert(position: Int, label: String? = null, detailedAction: String?) =
        g_menu_insert(gMenuPointer, position, label, detailedAction)

    fun insertItem(position: Int, item: MenuItem) = g_menu_insert_item(gMenuPointer, position, item.gMenuItemPointer)

    fun insertSection(position: Int, label: String? = null, section: MenuModel) =
        g_menu_insert_section(gMenuPointer, position, label, section.gMenuModelPointer)

    fun insertSubmenu(position: Int, label: String? = null, submenu: MenuModel) =
        g_menu_insert_submenu(gMenuPointer, position, label, submenu.gMenuModelPointer)

    fun prepend(label: String? = null, detailedAction: String? = null) =
        g_menu_prepend(gMenuPointer, label, detailedAction)

    fun prependItem(item: MenuItem) = g_menu_prepend_item(gMenuPointer, item.gMenuItemPointer)

    fun prependSection(label: String? = null, section: MenuModel) =
        g_menu_prepend_section(gMenuPointer, label, section.gMenuModelPointer)

    fun prependSubmenu(label: String? = null, submenu: MenuModel) =
        g_menu_prepend_submenu(gMenuPointer, label, submenu.gMenuModelPointer)

    fun remove(position: Int) = g_menu_remove(gMenuPointer, position)
    fun removeAll() = g_menu_remove_all(gMenuPointer)

    companion object : ObjectCompanion<Menu>(MenuTypeInfo)
}

val MenuTypeInfo = BuiltinTypeInfo(
    "GMenu",
    G_TYPE_MENU,
    -1,
    -1,
    ::Menu
)