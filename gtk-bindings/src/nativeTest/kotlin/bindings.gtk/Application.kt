package bindings.gtk

import bindings.gio.GApplication
import bindings.gobject.ObjectCompanion
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_DEFAULT_FLAGS
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

open class Application(pointer: CPointer<*>) : GApplication(pointer) {

    @Suppress("UNCHECKED_CAST")
    val gtkApplicationPointer get() = gPointer as GtkApplication_autoptr

    companion object : ObjectCompanion<Application>(
        BuiltinTypeInfo(
            "GtkApplication",
            GTK_TYPE_APPLICATION,
            sizeOf<GtkApplicationClass>(),
            sizeOf<GtkApplication>(),
            ::Application
        )
    )

    constructor(
        applicationId: String,
        flags: GApplicationFlags = G_APPLICATION_DEFAULT_FLAGS
    ) : this(gtk_application_new(applicationId, flags)!!)

    fun onActivate(func: () -> Unit) {
        g_signal_connect_data(
            gPointer.reinterpret(), "activate", onActivateCallbackFunc,
            StableRef.create(func).asCPointer(), null /* TODO Dispose stableref */, 0
        )
    }


//
//    /*
//     * ACTIONS
//     * TODO: move this to an interface for all classes implementing ActionMap?
//     */
//
//    /**
//     * Add the given [action] to the application action map
//     */
//    fun add_action(action: GSimpleAction) {
//        g_action_map_add_action(gPointer.reinterpret(), action.gPointer.reinterpret())
//    }
//
//    fun add_action(name: String, handler: ActionHandler) {
//        val action = GSimpleAction(name, handler)
//        g_action_map_add_action(gPointer.reinterpret(), action.gPointer.reinterpret())
//    }

}


private val onActivateCallbackFunc: GCallback =
    staticCFunction { _: gpointer?, data: gpointer? ->
        data?.asStableRef<() -> Unit>()?.get()?.invoke()
        Unit
    }.reinterpret()