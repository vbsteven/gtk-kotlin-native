package bindings.adw

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.ADW_TYPE_APPLICATION
import native.adwaita.AdwApplication
import native.adwaita.AdwApplicationClass
import native.adwaita.adw_application_new
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_DEFAULT_FLAGS
import bindings.gtk.Application as GtkApplication

class Application : GtkApplication {

    val adwApplicationPointer get() = gApplicationPointer.asTypedPointer<AdwApplication>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(applicationId: String, flags: GApplicationFlags = G_APPLICATION_DEFAULT_FLAGS) : this(
        adw_application_new(applicationId, flags)!!
    )

    companion object : ObjectCompanion<Application>(ApplicationTypeInfo)
}

private val ApplicationTypeInfo = BuiltinTypeInfo(
    "AdwApplication",
    ADW_TYPE_APPLICATION,
    sizeOf<AdwApplicationClass>(),
    sizeOf<AdwApplication>(),
    ::Application
)