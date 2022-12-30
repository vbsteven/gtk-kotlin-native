package usertypes

import bindings.gobject.Object
import internal.objectproperties.ObjectPropertyDelegate
import kotlinx.cinterop.CPointer
import native.gobject.GParamFlags
import native.gobject.GParamSpec
import native.gobject.GValue
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty

abstract class KGObjectProperty<OBJECT_TYPE : Object, PARAM_TYPE : Any?>(
    val name: String,
    val nick: String?,
    val blurb: String?,
    val defaultValue: PARAM_TYPE,
    val flags: GParamFlags,
    private val kProperty: KMutableProperty1<OBJECT_TYPE, PARAM_TYPE>
) {

    protected val initialValue: PARAM_TYPE = defaultValue

    abstract val paramSpec: CPointer<GParamSpec>

    abstract fun extractValueFromGValue(gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>): PARAM_TYPE
    abstract fun insertValueIntoGValue(value: PARAM_TYPE, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>)

    internal operator fun provideDelegate(thisRef: Object, property: KProperty<*>): ObjectPropertyDelegate<PARAM_TYPE> {
        return ObjectPropertyDelegate(name, defaultValue)
    }

    @Suppress("UNCHECKED_CAST")
    fun getPropertyValue(thisRef: Any, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        val value = kProperty.getValue(thisRef as OBJECT_TYPE, kProperty)
        insertValueIntoGValue(value, gValue, paramSpec)
    }

    @Suppress("UNCHECKED_CAST")
    fun setPropertyValue(thisRef: Any, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        kProperty.setValue(thisRef as OBJECT_TYPE, kProperty, extractValueFromGValue(gValue, paramSpec))
    }

}
