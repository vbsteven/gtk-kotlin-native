package bindings.gtk

import bindings.gio.ActionMap
import bindings.gio.Application
import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.internal.staticStableRefDestroy
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gio.GActionMap
import native.gio.GApplicationFlags
import native.gio.G_APPLICATION_FLAGS_NONE
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.*

open class Application(pointer: CPointer<*>) : Application(pointer), ActionMap {

    val gtkApplicationPointer get() = gPointer.asTypedPointer<GtkApplication>()

    override val gActionMapPointer get() = gPointer.asTypedPointer<GActionMap>()

    companion object {
        val typeInfo =
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

// TODO would this make sence in GApplication instead?
private val onActivateCallbackFunc: GCallback =
    staticCFunction { _: gpointer?, data: gpointer? ->
        data?.asStableRef<() -> Unit>()?.get()?.invoke()
        Unit
    }.reinterpret()