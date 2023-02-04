package bindings.gtk

import bindings.gio.ListModel
import bindings.gio.MenuModel
import bindings.gio.asListModel
import bindings.gio.asMenuModel
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class ColumnView : Widget {
    val gtkColumnViewPointer get() = gtkWidgetPointer.asTypedPointer<GtkColumnView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(model: SelectionModel? = null) : this(gtk_column_view_new(model?.gtkSelectionModelPointer)!!)

    fun appendColumn(column: ColumnViewColumn) =
        gtk_column_view_append_column(gtkColumnViewPointer, column.gtkColumnViewColumnPointer)

    fun insertColumn(position: Int, column: ColumnViewColumn) =
        gtk_column_view_insert_column(gtkColumnViewPointer, position.toUInt(), column.gtkColumnViewColumnPointer)

    fun removeColumn(column: ColumnViewColumn) =
        gtk_column_view_remove_column(gtkColumnViewPointer, column.gtkColumnViewColumnPointer)

    fun sortByColumn(column: ColumnViewColumn?, direction: GtkSortType) =
        gtk_column_view_sort_by_column(gtkColumnViewPointer, column?.gtkColumnViewColumnPointer, direction)

    val columns: ListModel get() = gtk_column_view_get_columns(gtkColumnViewPointer)!!.asListModel()

    var enableRubberband: Boolean
        get() = gtk_column_view_get_enable_rubberband(gtkColumnViewPointer).boolean
        set(value) = gtk_column_view_set_enable_rubberband(gtkColumnViewPointer, value.gboolean)

    var model: SelectionModel?
        get() = gtk_column_view_get_model(gtkColumnViewPointer)?.asSelectionModel()
        set(value) = gtk_column_view_set_model(gtkColumnViewPointer, value?.gtkSelectionModelPointer)

    var reorderable: Boolean
        get() = gtk_column_view_get_reorderable(gtkColumnViewPointer).boolean
        set(value) = gtk_column_view_set_reorderable(gtkColumnViewPointer, value.gboolean)

    var showColumnSeparators: Boolean
        get() = gtk_column_view_get_show_column_separators(gtkColumnViewPointer).boolean
        set(value) = gtk_column_view_set_show_column_separators(gtkColumnViewPointer, value.gboolean)

    var showRowSeparators: Boolean
        get() = gtk_column_view_get_show_row_separators(gtkColumnViewPointer).boolean
        set(value) = gtk_column_view_set_show_row_separators(gtkColumnViewPointer, value.gboolean)

    var singleClickActivate: Boolean
        get() = gtk_column_view_get_single_click_activate(gtkColumnViewPointer).boolean
        set(value) = gtk_column_view_set_single_click_activate(gtkColumnViewPointer, value.gboolean)

    // TODO add sorter getter/setter with GtkSorter


    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_COLUMN_VIEW,
            ::ColumnView
        )
    }
}

class ColumnViewColumn : Object {
    val gtkColumnViewColumnPointer get() = gPointer.asTypedPointer<GtkColumnViewColumn>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(
        title: String? = null,
        factory: ListItemFactory? = null
    ) : this(gtk_column_view_column_new(title, factory?.gtkListItemFactoryPointer)!!)

    val columnView: ColumnView? get() = gtk_column_view_column_get_column_view(gtkColumnViewColumnPointer)?.asColumnView()

    var expand: Boolean
        get() = gtk_column_view_column_get_expand(gtkColumnViewColumnPointer).boolean
        set(value) = gtk_column_view_column_set_expand(gtkColumnViewColumnPointer, value.gboolean)

    var factory: ListItemFactory?
        get() = gtk_column_view_column_get_factory(gtkColumnViewColumnPointer)?.asListItemFactory()
        set(value) = gtk_column_view_column_set_factory(gtkColumnViewColumnPointer, value?.gtkListItemFactoryPointer)

    var fixedWidth: Int
        get() = gtk_column_view_column_get_fixed_width(gtkColumnViewColumnPointer)
        set(value) = gtk_column_view_column_set_fixed_width(gtkColumnViewColumnPointer, value)

    var headerMenu: MenuModel?
        get() = gtk_column_view_column_get_header_menu(gtkColumnViewColumnPointer)?.asMenuModel()
        set(value) = gtk_column_view_column_set_header_menu(gtkColumnViewColumnPointer, value?.gMenuModelPointer)

    var resizable: Boolean
        get() = gtk_column_view_column_get_resizable(gtkColumnViewColumnPointer).boolean
        set(value) = gtk_column_view_column_set_resizable(gtkColumnViewColumnPointer, value.gboolean)

    // TODO add sorter getter/setter with GtkSorter

    var title: String?
        get() = gtk_column_view_column_get_title(gtkColumnViewColumnPointer)?.toKString()
        set(value) = gtk_column_view_column_set_title(gtkColumnViewColumnPointer, value)

    var visible: Boolean
        get() = gtk_column_view_column_get_visible(gtkColumnViewColumnPointer).boolean
        set(value) = gtk_column_view_column_set_visible(gtkColumnViewColumnPointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(
            GTK_TYPE_COLUMN_VIEW_COLUMN,
            ::ColumnViewColumn
        )
    }
}