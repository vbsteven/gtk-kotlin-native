package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import native.gtk.*

class GridView : ListBase {

    val gtkGridViewPointer get() = gtkWidgetPointer.asTypedPointer<GtkGridView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(
        model: SelectionModel? = null,
        factory: ListItemFactory? = null
    ) : this(gtk_grid_view_new(model?.gtkSelectionModelPointer, factory?.gtkListItemFactoryPointer)!!)


    var enableRubberband: Boolean
        get() = gtk_grid_view_get_enable_rubberband(gtkGridViewPointer).boolean
        set(value) = gtk_grid_view_set_enable_rubberband(gtkGridViewPointer, value.gboolean)

    var factory: ListItemFactory?
        get() = gtk_grid_view_get_factory(gtkGridViewPointer)?.asListItemFactory()
        set(value) = gtk_grid_view_set_factory(gtkGridViewPointer, value?.gtkListItemFactoryPointer)

    var model: SelectionModel?
        get() = gtk_grid_view_get_model(gtkGridViewPointer)?.asSelectionModel()
        set(value) = gtk_grid_view_set_model(gtkGridViewPointer, value?.gtkSelectionModelPointer)

    var singleClickActivate: Boolean
        get() = gtk_grid_view_get_single_click_activate(gtkGridViewPointer).boolean
        set(value) = gtk_grid_view_set_single_click_activate(gtkGridViewPointer, value.gboolean)

    var minColumns: Int
        get() = gtk_grid_view_get_min_columns(gtkGridViewPointer).toInt()
        set(value) = gtk_grid_view_set_min_columns(gtkGridViewPointer, value.toUInt())

    var maxColumns: Int
        get() = gtk_grid_view_get_max_columns(gtkGridViewPointer).toInt()
        set(value) = gtk_grid_view_set_max_columns(gtkGridViewPointer, value.toUInt())

}