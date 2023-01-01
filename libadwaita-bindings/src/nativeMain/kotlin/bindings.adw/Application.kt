package bindings.adw

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.ADW_TYPE_APPLICATION
import native.adwaita.AdwApplication
import native.adwaita.AdwApplicationClass
import native.adwaita.adw_application_new
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_FLAGS_NONE
import bindings.gtk.Application as GtkApplication

open class Application : GtkApplication {

    val adwApplicationPointer get() = gApplicationPointer.asTypedPointer<AdwApplication>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor(applicationId: String, flags: GApplicationFlags = G_APPLICATION_FLAGS_NONE) : this(
        adw_application_new(applicationId, flags)!!
    )

    companion object {
        val Type = BuiltinTypeInfo(
            "AdwApplication",
            ADW_TYPE_APPLICATION,
            sizeOf<AdwApplicationClass>(),
            sizeOf<AdwApplication>(),
            ::Application
        )
    }
}
