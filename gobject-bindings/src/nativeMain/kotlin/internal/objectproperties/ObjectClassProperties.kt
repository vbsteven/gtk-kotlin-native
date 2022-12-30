package internal.objectproperties

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import native.gobject.*
import usertypes.KGObjectProperty

class ObjectClassProperties(
    private val objectClassPointer: CValuesRef<GObjectClass>
) {
    private var propertyIndex = 0.toUInt()
    private val propertyMap: MutableMap<UInt, KGObjectProperty<*, *>> = mutableMapOf()

    fun installProperty(
        property: KGObjectProperty<*, *>
    ) {
        propertyMap[++propertyIndex] = property

        val paramSpec = property.paramSpec
        g_object_class_install_property(objectClassPointer, propertyIndex, paramSpec)
        g_param_spec_unref(paramSpec) // TODO is this correct?
    }

    /**
     * Called by gobject when a property needs to be set
     */
    fun setPropertyValue(thisRef: Any, propertyId: UInt, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        propertyMap[propertyId]?.setPropertyValue(thisRef, gValue, paramSpec)
    }

    /**
     * Called by gobject when a property needs to be get
     */
    fun getPropertyValue(thisRef: Any, propertyId: UInt, gValue: CPointer<GValue>, paramSpec: CPointer<GParamSpec>) {
        propertyMap[propertyId]?.getPropertyValue(thisRef, gValue, paramSpec)
    }

}