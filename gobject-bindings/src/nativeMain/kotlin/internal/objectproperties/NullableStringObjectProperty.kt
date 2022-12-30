package internal.objectproperties

import bindings.gobject.Object
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gobject.*
import usertypes.KGObjectProperty
import kotlin.reflect.KMutableProperty1

internal class NullableStringObjectProperty<OBJECT_TYPE : Object>(
    kProperty: KMutableProperty1<OBJECT_TYPE, String?>,
    name: String,
    nick: String? = null,
    blurb: String? = null,
    defaultValue: String? = null,
    flags: GParamFlags = G_PARAM_READWRITE,
) : KGObjectProperty<OBJECT_TYPE, String?>(name, nick, blurb, defaultValue, flags, kProperty) {

    override val paramSpec: CPointer<GParamSpec>
        get() = g_param_spec_string(name, nick, blurb, defaultValue, flags)!!

    override fun extractValueFromGValue(gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>): String? {
        return g_value_get_string(gValue)?.toKString()
    }

    override fun insertValueIntoGValue(value: String?, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        g_value_set_string(gValue, value)
    }

}