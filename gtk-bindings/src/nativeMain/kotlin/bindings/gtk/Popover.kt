package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gtk.*

open class Popover : Widget, ShortcutManager {
    val gtkPopoverPointer get() = gtkWidgetPointer.asTypedPointer<GtkPopover>()

    override val gtkShortcutManagerPointer get() = gtkWidgetPointer.asTypedPointer<GtkShortcutManager>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_popover_new()!!)

    var autohide: Boolean
        get() = gtk_popover_get_autohide(gtkPopoverPointer).boolean
        set(value) = gtk_popover_set_autohide(gtkPopoverPointer, value.gboolean)

    var cascadePopdown: Boolean
        get() = gtk_popover_get_cascade_popdown(gtkPopoverPointer).boolean
        set(value) = gtk_popover_set_cascade_popdown(gtkPopoverPointer, value.gboolean)

    var child: Widget?
        get() = gtk_popover_get_child(gtkPopoverPointer)?.asWidget()
        set(value) = gtk_popover_set_child(gtkPopoverPointer, value?.gtkWidgetPointer)

    var hasArrow: Boolean
        get() = gtk_popover_get_has_arrow(gtkPopoverPointer).boolean
        set(value) = gtk_popover_set_has_arrow(gtkPopoverPointer, value.gboolean)

    var mnemonicsVisible: Boolean
        get() = gtk_popover_get_mnemonics_visible(gtkPopoverPointer).boolean
        set(value) = gtk_popover_set_mnemonics_visible(gtkPopoverPointer, value.gboolean)

    var offset: PopoverOffset
        get() = memScoped {
            val xVar = alloc<IntVar>()
            val yVar = alloc<IntVar>()
            gtk_popover_get_offset(gtkPopoverPointer, xVar.ptr, yVar.ptr)
            // TODO gtk docs mention this argument can be set to NULL?
            PopoverOffset(xVar.value, yVar.value)
        }
        set(value) = gtk_popover_set_offset(gtkPopoverPointer, value.x, value.y)

    // TODO pointingTo property with GdkRectangle

    var position: GtkPositionType
        get() = gtk_popover_get_position(gtkPopoverPointer)
        set(value) = gtk_popover_set_position(gtkPopoverPointer, value)

    fun popup() = gtk_popover_popup(gtkPopoverPointer)

    fun popdown() = gtk_popover_popdown(gtkPopoverPointer)

    /**
     * Allocate a size for the popover.
     *
     * This function needs to be called in size-allocate by widgets who have a GtkPopover as child. When using a
     * layout manager, this is happening automatically.
     *
     * Use [popup] instead.
     */
    fun present() = gtk_popover_present(gtkPopoverPointer)

    fun setDefaultWidget(widget: Widget?) = gtk_popover_set_default_widget(gtkPopoverPointer, widget?.gtkWidgetPointer)

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "GtkPopover",
            GTK_TYPE_POPOVER,
            sizeOf<GtkPopoverClass>(),
            sizeOf<GtkPopover>(),
            ::Popover
        )
    }
}

data class PopoverOffset(val x: Int, val y: Int)
