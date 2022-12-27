package bindings.gtk

import kotlinx.cinterop.CPointer
import native.gtk.GtkOrientable
import native.gtk.GtkOrientation
import native.gtk.gtk_orientable_get_orientation
import native.gtk.gtk_orientable_set_orientation

interface Orientable {

    val gtkOrientablePointer: CPointer<GtkOrientable>

    var orientation: GtkOrientation
        get() = gtk_orientable_get_orientation(gtkOrientablePointer)
        set(value) = gtk_orientable_set_orientation(gtkOrientablePointer, value)
}
