package internal.objectproperties

import bindings.gobject.Object
import kotlinx.cinterop.CPointer
import native.gobject.*
import usertypes.KGObjectProperty
import kotlin.reflect.KMutableProperty1

class IntObjectProperty<OBJECT_TYPE : Object>(
    kProperty: KMutableProperty1<OBJECT_TYPE, Int>,
    name: String,
    nick: String?,
    blurb: String?,
    val minimum: Int,
    val maximum: Int,
    defaultValue: Int,
    flags: GParamFlags,
) : KGObjectProperty<OBJECT_TYPE, Int>(name, nick, blurb, defaultValue, flags, kProperty) {
    override val paramSpec: CPointer<GParamSpec> get() = g_param_spec_int(name, nick, blurb, minimum, maximum, defaultValue, flags)!!

    override fun extractValueFromGValue(gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>): Int {
        return g_value_get_int(gValue)
    }

    override fun insertValueIntoGValue(value: Int, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        return g_value_set_int(gValue, value)
    }

}