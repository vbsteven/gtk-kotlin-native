package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.gtk.*

class Switch : Widget, Actionable {
    val gtkSwitchPointer get() = gtkWidgetPointer.asTypedPointer<GtkSwitch>()

    override val gtkActionablePointer get() = gtkWidgetPointer.asTypedPointer<GtkActionable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_switch_new()!!)

    var state: Boolean
        get() = gtk_switch_get_state(gtkSwitchPointer).boolean
        set(value) = gtk_switch_set_state(gtkSwitchPointer, value.gboolean)

    var active: Boolean
        get() = gtk_switch_get_active(gtkSwitchPointer).boolean
        set(value) = gtk_switch_set_active(gtkSwitchPointer, value.gboolean)

    // TODO activate and state-set signals

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_SWITCH, ::Switch)
    }
}
