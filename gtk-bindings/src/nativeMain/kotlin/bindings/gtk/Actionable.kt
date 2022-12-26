package bindings.gtk

import bindings.glib.Variant
import bindings.glib.asVariant
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

interface Actionable {

    val gtkActionablePointer: CPointer<GtkActionable>

    var actionName: String?
        get() = gtk_actionable_get_action_name(gtkActionablePointer)?.toKString()
        set(value) = gtk_actionable_set_action_name(gtkActionablePointer, value)

    var actionTarget: Variant?
        get() = gtk_actionable_get_action_target_value(gtkActionablePointer)?.asVariant()
        set(value) = gtk_actionable_set_action_target_value(gtkActionablePointer, value?.variantPointer)

    fun setDetailedActionName(detailedActionName: String) {
        gtk_actionable_set_detailed_action_name(gtkActionablePointer, detailedActionName)
    }

}