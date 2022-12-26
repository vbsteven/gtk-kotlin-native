package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

open class Box : Widget, Orientable {

    val gtkBoxPointer get() = widgetPointer.asTypedPointer<GtkBox>()

    override val gtkOrientablePointer get() = gtkBoxPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(orientation: GtkOrientation, spacing: Int) : this(gtk_box_new(orientation, spacing)!!)

    fun append(child: Widget) = gtk_box_append(gtkBoxPointer, child.widgetPointer)
    fun appendAll(vararg children: Widget) = children.forEach(::append)
    fun appendAll(children: List<Widget>) = children.forEach(::append)

    fun prepend(child: Widget) = gtk_box_prepend(gtkBoxPointer, child.widgetPointer)
    fun remove(child: Widget) = gtk_box_remove(gtkBoxPointer, child.widgetPointer)
    fun reorderChildAfter(child: Widget, sibling: Widget?) =
        gtk_box_reorder_child_after(gtkBoxPointer, child.widgetPointer, sibling?.widgetPointer)

    var baselinePosition: GtkBaselinePosition
        get() = gtk_box_get_baseline_position(gtkBoxPointer)
        set(value) = gtk_box_set_baseline_position(gtkBoxPointer, value)

    var homogeneous: Boolean
        get() = gtk_box_get_homogeneous(gtkBoxPointer).boolean
        set(value) = gtk_box_set_homogeneous(gtkBoxPointer, value.gboolean)

    var spacing: Int
        get() = gtk_box_get_spacing(gtkBoxPointer)
        set(value) = gtk_box_set_spacing(gtkBoxPointer, value)


    companion object : ObjectCompanion<Box>(BoxTypeInfo)
}

val BoxTypeInfo = BuiltinTypeInfo(
    "GtkBox",
    GTK_TYPE_BOX,
    sizeOf<GtkBoxClass>(),
    sizeOf<GtkBox>(),
    ::Box
)

fun CPointer<GtkBox>.asBox(): Box = Box(this)
