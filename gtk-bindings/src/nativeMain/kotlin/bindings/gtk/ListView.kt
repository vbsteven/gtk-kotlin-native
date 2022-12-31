package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import native.gtk.*

class ListView : ListBase {
    val gtkListViewPointer get() = gtkWidgetPointer.asTypedPointer<GtkListView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(model: SelectionModel?, factory: ListItemFactory?) : this(
        gtk_list_view_new(
            model?.gtkSelectionModelPointer,
            factory?.gtkListItemFactoryPointer
        )!!
    )

    var enableRubberband: Boolean
        get() = gtk_list_view_get_enable_rubberband(gtkListViewPointer).boolean
        set(value) = gtk_list_view_set_enable_rubberband(gtkListViewPointer, value.gboolean)

    var factory: ListItemFactory?
        get() = gtk_list_view_get_factory(gtkListViewPointer)?.asListItemFactory()
        set(value) = gtk_list_view_set_factory(gtkListViewPointer, value?.gtkListItemFactoryPointer)

    var model: SelectionModel?
        get() = gtk_list_view_get_model(gtkListViewPointer)?.asSelectionModel()
        set(value) = gtk_list_view_set_model(gtkListViewPointer, value?.gtkSelectionModelPointer)

    var showSeparators: Boolean
        get() = gtk_list_view_get_show_separators(gtkListViewPointer).boolean
        set(value) = gtk_list_view_set_show_separators(gtkListViewPointer, value.gboolean)

    var singleClickActivate: Boolean
        get() = gtk_list_view_get_single_click_activate(gtkListViewPointer).boolean
        set(value) = gtk_list_view_set_single_click_activate(gtkListViewPointer, value.gboolean)
}

