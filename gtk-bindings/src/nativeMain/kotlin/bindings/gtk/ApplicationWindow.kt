package bindings.gtk

import bindings.gio.ActionMap
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gio.GActionMap
import native.gtk.*

open class ApplicationWindow : Window, ActionMap {

    val gtkApplicationWindowPointer get() = gtkWindowPointer.asTypedPointer<GtkApplicationWindow>()

    override val gActionMapPointer get() = gtkWindowPointer.asTypedPointer<GActionMap>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(application: Application) : this(gtk_application_window_new(application.gtkApplicationPointer)!!)

    val id: UInt get() = gtk_application_window_get_id(gtkApplicationWindowPointer)

    // TODO helpOverlay property as GtkShortcutsWindow

    var showMenubar: Boolean
        get() = gtk_application_window_get_show_menubar(gtkApplicationWindowPointer).boolean
        set(value) = gtk_application_window_set_show_menubar(gtkApplicationWindowPointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(
            "GtkApplicationWindow",
            GTK_TYPE_APPLICATION_WINDOW,
            sizeOf<GtkApplicationWindowClass>(),
            sizeOf<GtkApplicationWindow>(),
            ::ApplicationWindow
        )
    }
}
