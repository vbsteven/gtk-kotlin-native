package bindings.gio

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gio.*

open class Application(pointer: CPointer<*>) : Object(pointer), ActionMap {

    val gApplicationPointer get() = this.gPointer.asTypedPointer<GApplication>()

    override val gActionMapPointer get() = gPointer.asTypedPointer<GActionMap>()

    constructor(applicationId: String, flags: GApplicationFlags = G_APPLICATION_FLAGS_NONE)
            : this(g_application_new(applicationId, flags)!!)

    var applicationId: String?
        get() = g_application_get_application_id(gApplicationPointer)?.toKString()
        set(value) = g_application_set_application_id(gApplicationPointer, value)

    var flags: GApplicationFlags
        get() = g_application_get_flags(gApplicationPointer)
        set(value) = g_application_set_flags(gApplicationPointer, value)

    val isBusy: Boolean get() = g_application_get_is_busy(gApplicationPointer).boolean

    val isRegistered: Boolean get() = g_application_get_is_registered(gApplicationPointer).boolean

    val isRemote: Boolean get() = g_application_get_is_remote(gApplicationPointer).boolean

    fun markBusy() = g_application_mark_busy(gApplicationPointer)
    fun unmarkBusy() = g_application_unmark_busy(gApplicationPointer)

    fun hold() = g_application_hold(gApplicationPointer)
    fun release() = g_application_release(gApplicationPointer)


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
        val Type = BuiltinTypeInfo(
            "GApplication",
            G_TYPE_APPLICATION,
            sizeOf<GApplicationClass>(),
            sizeOf<GApplication>(),
            ::Application
        )
    }
}
