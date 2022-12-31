package bindings.gtk

import bindings.gobject.*
import kotlinx.cinterop.CPointer
import native.gtk.*

class ListItem : Object {

    val gtkListItemPointer get() = gPointer.asTypedPointer<GtkListItem>()

    constructor(pointer: CPointer<*>) : super(pointer)

    var activatable: Boolean
        get() = gtk_list_item_get_activatable(gtkListItemPointer).boolean
        set(value) = gtk_list_item_set_activatable(gtkListItemPointer, value.gboolean)

    var child: Widget?
        get() = gtk_list_item_get_child(gtkListItemPointer)?.asWidget()
        set(value) = gtk_list_item_set_child(gtkListItemPointer, value?.gtkWidgetPointer)

    /**
     * The position in the model that this item currently displays
     *
     * Returns null if unbound
     */
    val position: Int?
        get() {
            val i = gtk_list_item_get_position(gtkListItemPointer)
            return if (i != GTK_INVALID_LIST_POSITION) i.toInt() else null
        }

    var selectable: Boolean
        get() = gtk_list_item_get_selectable(gtkListItemPointer).boolean
        set(value) = gtk_list_item_set_selectable(gtkListItemPointer, value.gboolean)

    val selected: Boolean
        get() = gtk_list_item_get_selected(gtkListItemPointer).boolean

    val item: Object? = gtk_list_item_get_item(gtkListItemPointer)?.asObject()

}