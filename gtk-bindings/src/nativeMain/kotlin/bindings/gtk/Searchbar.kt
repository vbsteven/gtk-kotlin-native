package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.*

class SearchBar : Widget {
    val gtkSearchBarPointer get() = gtkWidgetPointer.asTypedPointer<GtkSearchBar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_search_bar_new()!!)

    fun connectEntry(entry: Editable) = gtk_search_bar_connect_entry(gtkSearchBarPointer, entry.gtkEditablePointer)

    var child: Widget?
        get() = gtk_search_bar_get_child(gtkSearchBarPointer)?.asWidget()
        set(value) = gtk_search_bar_set_child(gtkSearchBarPointer, value?.gtkWidgetPointer)

    var keyCaptureWidget: Widget?
        get() = gtk_search_bar_get_key_capture_widget(gtkSearchBarPointer)?.asWidget()
        set(value) = gtk_search_bar_set_key_capture_widget(gtkSearchBarPointer, value?.gtkWidgetPointer)

    var searchMode: Boolean
        get() = gtk_search_bar_get_search_mode(gtkSearchBarPointer).boolean
        set(value) = gtk_search_bar_set_search_mode(gtkSearchBarPointer, value.gboolean)

    var showCloseButton: Boolean
        get() = gtk_search_bar_get_show_close_button(gtkSearchBarPointer).boolean
        set(value) = gtk_search_bar_set_show_close_button(gtkSearchBarPointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_SEARCH_BAR, ::SearchBar)
    }
}