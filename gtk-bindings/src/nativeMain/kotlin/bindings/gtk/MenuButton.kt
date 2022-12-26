package bindings.gtk

import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class MenuButton : Widget {

    val gtkMenuButtonPointer get() = gPointer.asTypedPointer<GtkMenuButton>()

    constructor() : super(gtk_menu_button_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var isPrimary: Boolean
        get() = gtk_menu_button_get_primary(gtkMenuButtonPointer).boolean
        set(value) = gtk_menu_button_set_primary(gtkMenuButtonPointer, value.gboolean)

    var label: String
        get() = gtk_menu_button_get_label(gtkMenuButtonPointer)?.toKString() ?: ""
        set(value) = gtk_menu_button_set_label(gtkMenuButtonPointer, value)

    var iconName: String?
        get() = gtk_menu_button_get_icon_name(gtkMenuButtonPointer)?.toKString()
        set(value) = gtk_menu_button_set_icon_name(gtkMenuButtonPointer, value)

    var alwaysShowArrow: Boolean
        get() = gtk_menu_button_get_always_show_arrow(gtkMenuButtonPointer).boolean
        set(value) = gtk_menu_button_set_always_show_arrow(gtkMenuButtonPointer, value.gboolean)

    var child: Widget?
        get() = gtk_menu_button_get_child(gtkMenuButtonPointer)?.asWidget()
        set(value) = gtk_menu_button_set_child(gtkMenuButtonPointer, value?.widgetPointer)

    var direction: GtkArrowType
        get() = gtk_menu_button_get_direction(gtkMenuButtonPointer)
        set(value) = gtk_menu_button_set_direction(gtkMenuButtonPointer, value)

    var hasFrame: Boolean
        get() = gtk_menu_button_get_has_frame(gtkMenuButtonPointer).boolean
        set(value) = gtk_menu_button_set_has_frame(gtkMenuButtonPointer, value.gboolean)

    var menuModel: MenuModel?
        get() = gtk_menu_button_get_menu_model(gtkMenuButtonPointer)?.asMenuModel()
        set(value) = gtk_menu_button_set_menu_model(gtkMenuButtonPointer, value?.gMenuModelPointer)

    // TODO add popover property

    // TODO add setCreatePopupHandler https://docs.gtk.org/gtk4/method.MenuButton.set_create_popup_func.html

    var useUnderline: Boolean
        get() = gtk_menu_button_get_use_underline(gtkMenuButtonPointer).boolean
        set(value) = gtk_menu_button_set_use_underline(gtkMenuButtonPointer, value.gboolean)

    companion object : ObjectCompanion<MenuButton>(MenuButtonTypeInfo)
}

val MenuButtonTypeInfo = BuiltinTypeInfo(
    "GtkMenuButton",
    GTK_TYPE_MENU_BUTTON,
    -1,
    -1,
    ::MenuButton
)