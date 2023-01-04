package bindings.gtk

import bindings.gio.ListModel
import bindings.gio.asListModel
import bindings.gobject.*
import bindings.util.toCStringList
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import native.gtk.*

class DropDown : Widget {

    val gtkDropDownPointer get() = gtkWidgetPointer.asTypedPointer<GtkDropDown>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_drop_down_new(null, null)!!)
    constructor(model: ListModel) : this(gtk_drop_down_new(model.gListModelPointer, null)!!)

    // TODO constructor with GtkExpression
    constructor(items: List<String>) : this(newFromStrings(items))
    constructor(vararg items: String) : this(newFromStrings(items.toList()))

    var enableSearch: Boolean
        get() = gtk_drop_down_get_enable_search(gtkDropDownPointer).boolean
        set(value) = gtk_drop_down_set_enable_search(gtkDropDownPointer, value.gboolean)

    // TODO GtkExpression property

    var selected: Int
        get() = gtk_drop_down_get_selected(gtkDropDownPointer).toInt()
        set(value) = gtk_drop_down_set_selected(gtkDropDownPointer, value.toUInt())

    val selectedItem: Object?
        get() = gtk_drop_down_get_selected_item(gtkDropDownPointer)?.asObject()

    var factory: ListItemFactory?
        get() = gtk_drop_down_get_factory(gtkDropDownPointer)?.asListItemFactory()
        set(value) = gtk_drop_down_set_factory(gtkDropDownPointer, value?.gtkListItemFactoryPointer)

    var listFactory: ListItemFactory?
        get() = gtk_drop_down_get_list_factory(gtkDropDownPointer)?.asListItemFactory()
        set(value) = gtk_drop_down_set_list_factory(gtkDropDownPointer, value?.gtkListItemFactoryPointer)

    var model: ListModel?
        get() = gtk_drop_down_get_model(gtkDropDownPointer)?.asListModel()
        set(value) = gtk_drop_down_set_model(gtkDropDownPointer, value?.gListModelPointer)

    var showArrow: Boolean
        get() = gtk_drop_down_get_show_arrow(gtkDropDownPointer).boolean
        set(value) = gtk_drop_down_set_show_arrow(gtkDropDownPointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_DROP_DOWN, ::DropDown)

        private fun newFromStrings(items: List<String>): CPointer<*> = memScoped {
            val strings = items.toCStringList(this)
            gtk_drop_down_new_from_strings(strings)!!
        }
    }
}