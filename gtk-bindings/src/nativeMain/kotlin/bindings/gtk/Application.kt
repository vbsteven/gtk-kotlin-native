package bindings.gtk

import bindings.gio.ActionMap
import bindings.gio.GApplication
import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gio.GActionMap
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_DEFAULT_FLAGS
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

open class Application(pointer: CPointer<*>) : GApplication(pointer), ActionMap {

    @Suppress("UNCHECKED_CAST")
    val gtkApplicationPointer get() = gPointer as GtkApplication_autoptr

    override val gActionMapPointer get() = gPointer.asTypedPointer<GActionMap>()

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

    var menuBar: MenuModel?
        get() = gtk_application_get_menubar(gtkApplicationPointer)?.asMenuModel()
        set(value) = gtk_application_set_menubar(gtkApplicationPointer, value?.gMenuModelPointer)

}

fun CPointer<GtkApplication>.asApplication(): Application = Application(this)

// TODO would this make sence in GApplication instead?
private val onActivateCallbackFunc: GCallback =
    staticCFunction { _: gpointer?, data: gpointer? ->
        data?.asStableRef<() -> Unit>()?.get()?.invoke()
        Unit
    }.reinterpret()