package bindings.gio

import bindings.glib.Variant
import bindings.glib.VariantType
import bindings.glib.asVariant
import bindings.glib.asVariantType
import bindings.gobject.boolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gio.*
import native.gobject.g_free

interface Action {

    val gActionPointer: CPointer<GAction>

    fun activate(parameter: Variant) {
        g_action_activate(gActionPointer, parameter.variantPointer)
    }

    fun changeState(value: Variant) = g_action_change_state(gActionPointer, value.variantPointer)

    val isEnabled: Boolean
        get() = g_action_get_enabled(gActionPointer).boolean

    val name: String
        get() = g_action_get_name(gActionPointer)!!.toKString()

    val parameterType: VariantType?
        get() = g_action_get_parameter_type(gActionPointer)?.asVariantType()

    val state: Variant?
        get() = g_action_get_state(gActionPointer)?.asVariant()

    val stateHint: Variant?
        get() = g_action_get_state_hint(gActionPointer)?.asVariant()

    val stateType: VariantType?
        get() = g_action_get_state_type(gActionPointer)?.asVariantType()

    companion object {
        fun nameIsValid(name: String) = g_action_name_is_valid(name).boolean
        // TODO parseDetailedName
        fun printDetailedName(actionName: String, targetValue: Variant?): String {
            val resultPointer = g_action_print_detailed_name(actionName, targetValue?.variantPointer)!!
            val result = resultPointer.toKString()
            g_free(resultPointer)
            return result
        }
    }
}
