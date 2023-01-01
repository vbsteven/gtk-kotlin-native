package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gio.GPermission
import native.gio.GPermissionClass
import native.gio.G_TYPE_PERMISSION

open class Permission : Object {
    val gPermissionPointer get() = gPointer.asTypedPointer<GPermission>()

    constructor(pointer: CPointer<*>) : super(pointer)

    // TODO implement

    companion object {
        val Type = BuiltinTypeInfo(
            "GPermission",
            G_TYPE_PERMISSION,
            sizeOf<GPermissionClass>(),
            sizeOf<GPermission>(),
            ::Permission
        )
    }
}