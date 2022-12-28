package bindings.adw

import bindings.adw.internal.staticStableRefDestroy
import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.*
import kotlinx.cinterop.*
import native.adwaita.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.GtkActionable
import native.gtk.GtkArrowType

class SplitButton : Widget, Actionable {
    val adwSplitButtonPointer get() = gtkWidgetPointer.asTypedPointer<AdwSplitButton>()
    override val gtkActionablePointer get() = gtkWidgetPointer.asTypedPointer<GtkActionable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_split_button_new()!!)

    var child: Widget?
        get() = adw_split_button_get_child(adwSplitButtonPointer)?.asWidget()
        set(value) = adw_split_button_set_child(adwSplitButtonPointer, value?.gtkWidgetPointer)

    var direction: GtkArrowType
        get() = adw_split_button_get_direction(adwSplitButtonPointer)
        set(value) = adw_split_button_set_direction(adwSplitButtonPointer, value)

    var dropdownTooltip: String
        get() = adw_split_button_get_dropdown_tooltip(adwSplitButtonPointer)?.toKString() ?: ""
        set(value) = adw_split_button_set_dropdown_tooltip(adwSplitButtonPointer, value)

    var iconName: String?
        get() = adw_split_button_get_icon_name(adwSplitButtonPointer)?.toKString()
        set(value) = adw_split_button_set_icon_name(adwSplitButtonPointer, value)

    var label: String?
        get() = adw_split_button_get_label(adwSplitButtonPointer)?.toKString()
        set(value) = adw_split_button_set_label(adwSplitButtonPointer, value)

    var menuModel: MenuModel?
        get() = adw_split_button_get_menu_model(adwSplitButtonPointer)?.asMenuModel()
        set(value) = adw_split_button_set_menu_model(adwSplitButtonPointer, value?.gMenuModelPointer)

    var popover: Popover?
        get() = adw_split_button_get_popover(adwSplitButtonPointer)?.asPopover()
        set(value) = adw_split_button_set_popover(adwSplitButtonPointer, value?.gtkPopoverPointer)

    var useUnderline: Boolean
        get() = adw_split_button_get_use_underline(adwSplitButtonPointer).boolean
        set(value) = adw_split_button_set_use_underline(adwSplitButtonPointer, value.gboolean)

    /**
     * Pops up the menu.
     */
    fun popup() = adw_split_button_popup(adwSplitButtonPointer)

    /**
     * Dismisses the menu.
     */
    fun popdown() = adw_split_button_popdown(adwSplitButtonPointer)

    /**
     * Connect [func] as a handler for the "clicked" signal.
     */
    fun onClicked(func: (SplitButton) -> Unit) {
        g_signal_connect_data(
            gPointer,
            "clicked", staticButtonClickedFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

}

private val staticButtonClickedFunc: GCallback =
    staticCFunction { buttonPointer: CPointer<AdwSplitButton>,
                      data: gpointer ->
        val button = buttonPointer.asSplitButton()
        val func = data!!.asStableRef<(SplitButton) -> Unit>().get()
        func.invoke(button)
    }.reinterpret()