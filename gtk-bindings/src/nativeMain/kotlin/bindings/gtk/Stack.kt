package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class Stack : Widget {
    val gtkStackPointer get() = gtkWidgetPointer.asTypedPointer<GtkStack>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_stack_new()!!)

    fun addChild(child: Widget) = gtk_stack_add_child(gtkStackPointer, child.gtkWidgetPointer)

    fun addNamed(child: Widget, name: String) = gtk_stack_add_named(gtkStackPointer, child.gtkWidgetPointer, name)

    fun addTitled(child: Widget, name: String, title: String) =
        gtk_stack_add_titled(gtkStackPointer, child.gtkWidgetPointer, name, title)

    fun getChildByName(name: String): Widget? = gtk_stack_get_child_by_name(gtkStackPointer, name)?.asWidget()

    var hhomogeneous: Boolean
        get() = gtk_stack_get_hhomogeneous(gtkStackPointer).boolean
        set(value) = gtk_stack_set_hhomogeneous(gtkStackPointer, value.gboolean)

    var interpolateSize: Boolean
        get() = gtk_stack_get_interpolate_size(gtkStackPointer).boolean
        set(value) = gtk_stack_set_interpolate_size(gtkStackPointer, value.gboolean)

    fun getPage(child: Widget): StackPage? = gtk_stack_get_page(gtkStackPointer, child.gtkWidgetPointer)?.asStackPage()

    /**
     * TODO: accessing this means taking ownership of the data
     */
    val pages: SelectionModel
        get() = gtk_stack_get_pages(gtkStackPointer)!!.asSelectionModel()

    fun remove(child: Widget) = gtk_stack_remove(gtkStackPointer, child.gtkWidgetPointer)

    var transitionDuration: Int
        get() = gtk_stack_get_transition_duration(gtkStackPointer).toInt()
        set(value) = gtk_stack_set_transition_duration(gtkStackPointer, value.toUInt())

    val transitionRunning: Boolean
        get() = gtk_stack_get_transition_running(gtkStackPointer).boolean

    var transitionType: GtkStackTransitionType
        get() = gtk_stack_get_transition_type(gtkStackPointer)
        set(value) = gtk_stack_set_transition_type(gtkStackPointer, value)

    var vhomogeneous: Boolean
        get() = gtk_stack_get_vhomogeneous(gtkStackPointer).boolean
        set(value) = gtk_stack_set_vhomogeneous(gtkStackPointer, value.gboolean)

    var visibleChild: Widget?
        get() = gtk_stack_get_visible_child(gtkStackPointer)?.asWidget()
        set(value) = gtk_stack_set_visible_child(gtkStackPointer, value?.gtkWidgetPointer)

    var visibleChildName: String?
        get() = gtk_stack_get_visible_child_name(gtkStackPointer)?.toKString()
        set(value) = gtk_stack_set_visible_child_name(gtkStackPointer, value)

    fun setVisibleChild(name: String, transitionType: GtkStackTransitionType) =
        gtk_stack_set_visible_child_full(gtkStackPointer, name, transitionType)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_STACK, ::Stack)
    }
}