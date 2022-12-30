package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class LinkButton : Button {
    val gtkLinkButtonPointer get() = gtkWidgetPointer.asTypedPointer<GtkLinkButton>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(uri: String) : this(gtk_link_button_new(uri)!!)
    constructor(uri: String, label: String) : this(gtk_link_button_new_with_label(uri, label)!!)

    var uri: String
        get() = gtk_link_button_get_uri(gtkLinkButtonPointer)!!.toKString()
        set(value) = gtk_link_button_set_uri(gtkLinkButtonPointer, value)

    var visited: Boolean
        get() = gtk_link_button_get_visited(gtkLinkButtonPointer).boolean
        set(value) = gtk_link_button_set_visited(gtkLinkButtonPointer, value.gboolean)

    // TODO activate-link signal with ability to return a boolean

}