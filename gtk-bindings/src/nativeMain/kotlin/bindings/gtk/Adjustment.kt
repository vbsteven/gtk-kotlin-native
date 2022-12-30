package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.*

class Adjustment : Object {
    val gtkAdjustmentPointer get() = gPointer.asTypedPointer<GtkAdjustment>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(
        value: Double,
        lower: Double,
        upper: Double,
        stepIncrement: Double,
        pageIncrement: Double,
        pageSize: Double
    ) : this(gtk_adjustment_new(value, lower, upper, stepIncrement, pageIncrement, pageSize)!!)

    fun clampPage(lower: Double, upper: Double) = gtk_adjustment_clamp_page(gtkAdjustmentPointer, lower, upper)

    fun configure(
        value: Double,
        lower: Double,
        upper: Double,
        stepIncrement: Double,
        pageIncrement: Double,
        pageSize: Double
    ) = gtk_adjustment_configure(gtkAdjustmentPointer, value, lower, upper, stepIncrement, pageIncrement, pageSize)

    var lower: Double
        get() = gtk_adjustment_get_lower(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_lower(gtkAdjustmentPointer, value)

    var upper: Double
        get() = gtk_adjustment_get_upper(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_upper(gtkAdjustmentPointer, value)

    var value: Double
        get() = gtk_adjustment_get_value(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_value(gtkAdjustmentPointer, value)

    var stepIncrement: Double
        get() = gtk_adjustment_get_step_increment(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_step_increment(gtkAdjustmentPointer, value)

    var pageIncrement: Double
        get() = gtk_adjustment_get_page_increment(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_page_increment(gtkAdjustmentPointer, value)

    var pageSize: Double
        get() = gtk_adjustment_get_page_size(gtkAdjustmentPointer)
        set(value) = gtk_adjustment_set_page_size(gtkAdjustmentPointer, value)
}