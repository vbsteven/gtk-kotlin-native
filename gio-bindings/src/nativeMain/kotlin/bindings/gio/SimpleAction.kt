package bindings.gio

import bindings.gio.internal.staticStableRefDestroy
import bindings.glib.Variant
import bindings.glib.VariantType
import bindings.glib.asVariant
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.gboolean
import kotlinx.cinterop.*
import native.gio.*
import native.gobject.GCallback
import native.gobject.GVariant
import native.gobject.g_signal_connect_data
import native.gobject.gpointer

class SimpleAction : Object, Action {

    override val gActionPointer get() = gPointer.asTypedPointer<GAction>()
    val gSimpleActionPointer get() = gPointer.asTypedPointer<GSimpleAction>()

    constructor(name: String, parameterType: VariantType? = null) : this(
        g_simple_action_new(
            name,
            parameterType?.variantTypePointer
        )!!
    )

    constructor(name: String, parameterType: VariantType? = null, state: Variant) : this(
        g_simple_action_new_stateful(
            name,
            parameterType?.variantTypePointer,
            state.variantPointer
        )!!
    )

    constructor(pointer: CPointer<*>) : super(pointer)

    fun setEnabled(enabled: Boolean) = g_simple_action_set_enabled(gSimpleActionPointer, enabled.gboolean)
    fun setState(state: Variant?) = g_simple_action_set_state(gSimpleActionPointer, state?.variantPointer)
    fun setStateHint(stateHint: Variant?) = g_simple_action_set_state(gSimpleActionPointer, stateHint?.variantPointer)

    fun onActivate(func: (parameter: Variant?) -> Unit) {
        g_signal_connect_data(
            gPointer,
            "activate",
            staticActionCallbackFunc,
            StableRef.create(func).asCPointer(),
            staticStableRefDestroy,
            0
        )
    }

}


private val staticActionCallbackFunc: GCallback =
    staticCFunction { action: CPointer<GAction>,
                      param: CPointer<GVariant>,
                      data: gpointer ->
        data.asStableRef<(Variant?) -> Unit>()
            .get()
            .invoke(param?.asVariant())
        // TODO add param to the callback
    }.reinterpret()