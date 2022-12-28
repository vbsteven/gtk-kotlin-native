package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class StackPage : Object {
    val gtkStackPagePointer get() = gPointer.asTypedPointer<GtkStackPage>()

    constructor(pointer: CPointer<*>) : super(pointer)

    val child: Widget
        get() = gtk_stack_page_get_child(gtkStackPagePointer)!!.asWidget()

    var iconName: String?
        get() = gtk_stack_page_get_icon_name(gtkStackPagePointer)?.toKString()
        set(value) = gtk_stack_page_set_icon_name(gtkStackPagePointer, value)

    var name: String?
        get() = gtk_stack_page_get_name(gtkStackPagePointer)?.toKString()
        set(value) = gtk_stack_page_set_name(gtkStackPagePointer, value)

    var needsAttention: Boolean
        get() = gtk_stack_page_get_needs_attention(gtkStackPagePointer).boolean
        set(value) = gtk_stack_page_set_needs_attention(gtkStackPagePointer, value.gboolean)

    var title: String?
        get() = gtk_stack_page_get_title(gtkStackPagePointer)?.toKString()
        set(value) = gtk_stack_page_set_title(gtkStackPagePointer, value)

    var useUnderline: Boolean
        get() = gtk_stack_page_get_use_underline(gtkStackPagePointer).boolean
        set(value) = gtk_stack_page_set_use_underline(gtkStackPagePointer, value.gboolean)

    var visible: Boolean
        get() = gtk_stack_page_get_visible(gtkStackPagePointer).boolean
        set(value) = gtk_stack_page_set_visible(gtkStackPagePointer, value.gboolean)

}