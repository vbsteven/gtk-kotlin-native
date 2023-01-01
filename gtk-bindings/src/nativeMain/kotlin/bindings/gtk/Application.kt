package bindings.gtk

import bindings.gio.Application
import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.asTypedPointer
import bindings.gtk.internal.staticStableRefDestroy
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_FLAGS_NONE
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

open class Application(pointer: CPointer<*>) : Application(pointer) {

    val gtkApplicationPointer get() = gPointer.asTypedPointer<GtkApplication>()

    companion object {
        val Type =
            BuiltinTypeInfo(
                "GtkApplication",
                GTK_TYPE_APPLICATION,
                sizeOf<GtkApplicationClass>(),
                sizeOf<GtkApplication>(),
                ::Application
            )
    }

    constructor(
        applicationId: String,
        flags: GApplicationFlags = G_APPLICATION_FLAGS_NONE
    ) : this(gtk_application_new(applicationId, flags)!!)

    fun inhibit(window: Window?, flags: GtkApplicationInhibitFlags, reason: String?): UInt =
        gtk_application_inhibit(gtkApplicationPointer, window?.gtkWindowPointer, flags, reason)

    fun uninhibit(cookie: UInt) = gtk_application_uninhibit(gtkApplicationPointer, cookie)

    fun addWindow(window: Window) = gtk_application_add_window(gtkApplicationPointer, window?.gtkWindowPointer)

    fun getWindowById(id: UInt): Window? = gtk_application_get_window_by_id(gtkApplicationPointer, id)?.asWindow()

    fun removeWindow(window: Window) = gtk_application_remove_window(gtkApplicationPointer, window.gtkWindowPointer)


    fun onActivate(func: () -> Unit) {
        g_signal_connect_data(
            gPointer.reinterpret(),
            "activate",
            onActivateCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    var menuBar: MenuModel?
        get() = gtk_application_get_menubar(gtkApplicationPointer)?.asMenuModel()
        set(value) = gtk_application_set_menubar(gtkApplicationPointer, value?.gMenuModelPointer)

}

// TODO would this make sense in GApplication instead?
private val onActivateCallbackFunc: GCallback =
    staticCFunction { _: gpointer?, data: gpointer? ->
        data?.asStableRef<() -> Unit>()?.get()?.invoke()
        Unit
    }.reinterpret()