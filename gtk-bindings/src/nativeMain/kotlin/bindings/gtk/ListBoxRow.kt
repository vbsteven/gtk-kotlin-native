package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class ListBoxRow : Widget, Actionable {

    val gtkListBoxRowPointer get() = gPointer.asTypedPointer<GtkListBoxRow>()

    override val gtkActionablePointer get() = gPointer.asTypedPointer<GtkActionable>()

    constructor() : super(gtk_list_box_row_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var activatable: Boolean
        get() = gtk_list_box_row_get_activatable(gtkListBoxRowPointer).boolean
        set(value) = gtk_list_box_row_set_activatable(gtkListBoxRowPointer, value.gboolean)

    var selectable: Boolean
        get() = gtk_list_box_row_get_selectable(gtkListBoxRowPointer).boolean
        set(value) = gtk_list_box_row_set_selectable(gtkListBoxRowPointer, value.gboolean)

    var child: Widget?
        get() = gtk_list_box_row_get_child(gtkListBoxRowPointer)?.asWidget()
        set(value) = gtk_list_box_row_set_child(gtkListBoxRowPointer, value?.gtkWidgetPointer)

    var header: Widget?
        get() = gtk_list_box_row_get_header(gtkListBoxRowPointer)?.asWidget()
        set(value) = gtk_list_box_row_set_header(gtkListBoxRowPointer, value?.gtkWidgetPointer)

    val index: Int get() = gtk_list_box_row_get_index(gtkListBoxRowPointer)


    /**
     * Marks this row as changed.
     */
    fun changed() = gtk_list_box_row_changed(gtkListBoxRowPointer)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "GtkListBoxRow",
            GTK_TYPE_LIST_BOX_ROW,
            sizeOf<GtkListBoxRowClass>(),
            sizeOf<GtkListBoxRow>(),
            ::ListBoxRow
        )
    }
}