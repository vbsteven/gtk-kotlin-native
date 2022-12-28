package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkStackSidebar
import native.gtk.gtk_stack_sidebar_get_stack
import native.gtk.gtk_stack_sidebar_new
import native.gtk.gtk_stack_sidebar_set_stack

class StackSidebar : Widget {
    val gtkStackSidebarPointer get() = gtkWidgetPointer.asTypedPointer<GtkStackSidebar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_stack_sidebar_new()!!)

    var stack: Stack?
        get() = gtk_stack_sidebar_get_stack(gtkStackSidebarPointer)?.asStack()
        set(value) = gtk_stack_sidebar_set_stack(gtkStackSidebarPointer, value?.gtkStackPointer)

}