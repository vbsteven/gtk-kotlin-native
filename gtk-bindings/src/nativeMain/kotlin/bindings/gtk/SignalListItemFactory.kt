package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gtk.internal.staticStableRefDestroy
import kotlinx.cinterop.*
import native.gobject.GCallback
import native.gobject.g_signal_connect_data
import native.gobject.gpointer
import native.gtk.GtkListItem
import native.gtk.GtkSignalListItemFactory
import native.gtk.gtk_signal_list_item_factory_new

class SignalListItemFactory : ListItemFactory {
    val gtkSignalListItemFactoryPointer get() = gtkListItemFactoryPointer.asTypedPointer<GtkSignalListItemFactory>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_signal_list_item_factory_new()!!)

    fun onSetup(handler: (listItem: ListItem) -> Unit) {
        g_signal_connect_data(
            gtkListItemFactoryPointer,
            "setup",
            staticSignalListItemFactoryCallbackFunction,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    fun onBind(handler: (listItem: ListItem) -> Unit) {
        g_signal_connect_data(
            gtkListItemFactoryPointer,
            "bind",
            staticSignalListItemFactoryCallbackFunction,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    fun onUnbind(handler: (listItem: ListItem) -> Unit) {
        g_signal_connect_data(
            gtkListItemFactoryPointer,
            "unbind",
            staticSignalListItemFactoryCallbackFunction,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

    fun onTeardown(handler: (listItem: ListItem) -> Unit) {
        g_signal_connect_data(
            gtkListItemFactoryPointer,
            "teardown",
            staticSignalListItemFactoryCallbackFunction,
            StableRef.create(handler).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }


}

private val staticSignalListItemFactoryCallbackFunction: GCallback =
    staticCFunction { _: CPointer<GtkSignalListItemFactory>,
                      listItemPointer: CPointer<GtkListItem>,
                      data: gpointer ->
        data.asStableRef<(ListItem) -> Unit>()
            .get()
            .invoke(listItemPointer.asListItem())
    }.reinterpret()