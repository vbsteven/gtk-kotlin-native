package bindings.gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class StackSidebar : Widget {
    val gtkStackSidebarPointer get() = gtkWidgetPointer.asTypedPointer<GtkStackSidebar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_stack_sidebar_new()!!)

    var stack: Stack?
        get() = gtk_stack_sidebar_get_stack(gtkStackSidebarPointer)?.asStack()
        set(value) = gtk_stack_sidebar_set_stack(gtkStackSidebarPointer, value?.gtkStackPointer)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_STACK_SIDEBAR, ::StackSidebar)
    }
}