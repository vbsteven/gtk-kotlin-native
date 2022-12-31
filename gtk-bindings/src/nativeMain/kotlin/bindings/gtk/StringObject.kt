package bindings.gtk

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.GtkStringObject
import native.gtk.gtk_string_object_get_string
import native.gtk.gtk_string_object_new

class StringObject : Object {
    val gtkStringObjectPointer get() = gPointer.asTypedPointer<GtkStringObject>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(str: String) : this(gtk_string_object_new(str)!!)

    val string: String
        get() = gtk_string_object_get_string(gtkStringObjectPointer)!!.toKString()

    companion object {
        /**
         * // TODO can we make this generic for all final non-derivable wrapper classes?
         * @see [bindings.gobject.ObjectCompanion.fromObject]
         */
        fun fromObject(o: Object): StringObject {
            return StringObject(o.gPointer)
        }
    }
}