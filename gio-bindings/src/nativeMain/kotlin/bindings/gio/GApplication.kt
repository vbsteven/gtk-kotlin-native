package bindings.gio

import bindings.gobject.Object
import kotlinx.cinterop.CPointer
import native.gio.*

open class GApplication(pointer: CPointer<*>) : Object(pointer) {

    @Suppress("UNCHECKED_CAST")
    val gApplicationPointer get() = this.gPointer as GApplication_autoptr

    constructor(applicationId: String, flags: GApplicationFlags = G_APPLICATION_DEFAULT_FLAGS)
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
}