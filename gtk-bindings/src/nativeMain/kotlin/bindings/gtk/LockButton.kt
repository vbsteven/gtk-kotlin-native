package bindings.gtk

import bindings.gio.Permission
import bindings.gio.asPermission
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class LockButton : Button {
    val gtkLockButtonPointer get() = gtkWidgetPointer.asTypedPointer<GtkLockButton>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(permission: Permission?) : this(gtk_lock_button_new(permission?.gPermissionPointer)!!)

    var permission: Permission?
        get() = gtk_lock_button_get_permission(gtkLockButtonPointer)?.asPermission()
        set(value) = gtk_lock_button_set_permission(gtkLockButtonPointer, value?.gPermissionPointer)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_LOCK_BUTTON, ::LockButton)
    }
}