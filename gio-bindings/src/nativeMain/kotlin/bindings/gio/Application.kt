package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gio.*

open class Application(pointer: CPointer<*>) : Object(pointer) {

    val gApplicationPointer get() = this.gPointer.asTypedPointer<GApplication>()

    constructor(applicationId: String, flags: GApplicationFlags = G_APPLICATION_FLAGS_NONE)
            : this(g_application_new(applicationId, flags)!!)

    fun run() {
        g_application_run(gApplicationPointer, 0, null)
    }

    /**
     * Start running the application.
     *
     * This method will not return until the app has quit.
     */
    fun runApplication() {
        g_application_run(gApplicationPointer, 0, null)
    }

    /**
     * Quit the application.
     */
    fun quit() {
        g_application_quit(gApplicationPointer)
    }

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "GApplication",
            G_TYPE_APPLICATION,
            sizeOf<GApplicationClass>(),
            sizeOf<GApplication>(),
            ::Application
        )
    }
}
