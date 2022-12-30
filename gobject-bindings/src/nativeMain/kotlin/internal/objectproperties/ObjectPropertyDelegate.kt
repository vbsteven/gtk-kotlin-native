package internal.objectproperties

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import native.gobject.g_object_notify
import kotlin.reflect.KProperty

/**
 * A property delegate used for gobject properties.
 */
internal class ObjectPropertyDelegate<T : Any?>(
    private val propertyName: String,
    defaultValue: T,
) {
    private var internalValue: T = defaultValue

    /**
     * Called when the user gets a value in kotlin
     */
    operator fun getValue(thisRef: Object, property: KProperty<*>): T {
        return internalValue
    }

    /**
     * Called when the user sets a value in kotlin.
     *
     * Setting the value will trigger a gobject notify on the property.
     */
    operator fun setValue(thisRef: Object, property: KProperty<*>, s: T) {
        internalValue = s
        g_object_notify(thisRef.gPointer.asTypedPointer(), propertyName)
    }

}