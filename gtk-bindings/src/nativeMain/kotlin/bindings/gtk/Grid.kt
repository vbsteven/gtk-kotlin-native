package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class Grid : Widget, Orientable {
    val gtkGridPointer get() = gtkWidgetPointer.asTypedPointer<GtkGrid>()

    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_grid_new()!!)

    var baselineRow: Int
        get() = gtk_grid_get_baseline_row(gtkGridPointer)
        set(value) = gtk_grid_set_baseline_row(gtkGridPointer, value)

    var columnHomogeneous: Boolean
        get() = gtk_grid_get_column_homogeneous(gtkGridPointer).boolean
        set(value) = gtk_grid_set_column_homogeneous(gtkGridPointer, value.gboolean)

    var columnSpacing: Int
        get() = gtk_grid_get_column_spacing(gtkGridPointer).toInt()
        set(value) = gtk_grid_set_column_spacing(gtkGridPointer, value.toUInt())

    var rowHomogeneous: Boolean
        get() = gtk_grid_get_row_homogeneous(gtkGridPointer).boolean
        set(value) = gtk_grid_set_row_homogeneous(gtkGridPointer, value.gboolean)

    var rowSpacing: Int
        get() = gtk_grid_get_row_spacing(gtkGridPointer).toInt()
        set(value) = gtk_grid_set_row_spacing(gtkGridPointer, value.toUInt())

    fun getRowBaselinePosition(row: Int): GtkBaselinePosition = gtk_grid_get_row_baseline_position(gtkGridPointer, row)

    fun setRowBaselinePosition(row: Int, position: GtkBaselinePosition) =
        gtk_grid_set_row_baseline_position(gtkGridPointer, row, position)

    fun getChildAt(column: Int, row: Int): Widget? = gtk_grid_get_child_at(gtkGridPointer, column, row)?.asWidget()

    fun insertColumn(position: Int) = gtk_grid_insert_column(gtkGridPointer, position)

    fun insertNextTo(sibling: Widget, side: GtkPositionType) =
        gtk_grid_insert_next_to(gtkGridPointer, sibling.gtkWidgetPointer, side)

    fun insertRow(position: Int) = gtk_grid_insert_row(gtkGridPointer, position)

    // TODO queryChild fun

    fun remove(child: Widget) = gtk_grid_remove(gtkGridPointer, child.gtkWidgetPointer)

    fun removeColumn(position: Int) = gtk_grid_remove_column(gtkGridPointer, position)

    fun removeRow(position: Int) = gtk_grid_remove_row(gtkGridPointer, position)

    fun attach(child: Widget, column: Int, row: Int, width: Int, height: Int) =
        gtk_grid_attach(gtkGridPointer, child.gtkWidgetPointer, column, row, width, height)

    fun attachNextTo(child: Widget, sibling: Widget, side: GtkPositionType, width: Int, height: Int) =
        gtk_grid_attach_next_to(gtkGridPointer, child.gtkWidgetPointer, sibling.gtkWidgetPointer, side, width, height)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "GtkGrid",
            GTK_TYPE_GRID,
            sizeOf<GtkGridClass>(),
            sizeOf<GtkGrid>(),
            ::Grid
        )
    }
}