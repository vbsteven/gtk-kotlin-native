package bindings.gio

import bindings.gobject.asTypedPointer
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import native.gio.GSimplePermission
import native.gio.g_simple_permission_new

class SimplePermission : Permission {
    val gtkSimplePermissionPointer get() = gPermissionPointer.asTypedPointer<GSimplePermission>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(allowed: Boolean) : this(g_simple_permission_new(allowed.gboolean)!!)

}