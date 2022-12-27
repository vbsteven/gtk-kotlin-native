package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class HeaderBar : Widget {

    val gtkHeaderBarPointer get() = gPointer.asTypedPointer<GtkHeaderBar>()

    constructor() : super(gtk_header_bar_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var titleWidget: Widget?
        get() = gtk_header_bar_get_title_widget(gtkHeaderBarPointer)?.asWidget()
        set(value) = gtk_header_bar_set_title_widget(gtkHeaderBarPointer, value?.gtkWidgetPointer)

    var decorationLayout: String?
        get() = gtk_header_bar_get_decoration_layout(gtkHeaderBarPointer)?.toKString()
        set(value) = gtk_header_bar_set_decoration_layout(gtkHeaderBarPointer, value)

    var showTitleButtons: Boolean
        get() = gtk_header_bar_get_show_title_buttons(gtkHeaderBarPointer).boolean
        set(value) = gtk_header_bar_set_show_title_buttons(gtkHeaderBarPointer, value.gboolean)

    /**
     * Add [widget] to the end of the bar.
     */
    fun packEnd(widget: Widget) = gtk_header_bar_pack_end(gtkHeaderBarPointer, widget.gtkWidgetPointer)

    /**
     * Add [widget] to the start of the bar
     */
    fun packStart(widget: Widget) = gtk_header_bar_pack_start(gtkHeaderBarPointer, widget.gtkWidgetPointer)

    /**
     * Remove [widget] from the bar
     */
    fun remove(widget: Widget) = gtk_header_bar_remove(gtkHeaderBarPointer, widget.gtkWidgetPointer)

    companion object : ObjectCompanion<HeaderBar>(HeaderBarTypeInfo)
}

private val HeaderBarTypeInfo = BuiltinTypeInfo(
    "GtkHeaderBar",
    GTK_TYPE_HEADER_BAR,
    -1,
    -1,
    ::HeaderBar
)
