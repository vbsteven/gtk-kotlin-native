package bindings.gtk

import native.gtk.gtk_init

open class GtkTestBase {
    init {
        gtk_init()
    }
}