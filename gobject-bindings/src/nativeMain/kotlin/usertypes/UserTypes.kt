package usertypes

import bindings.gobject.Object
import internal.KGTypeInfo
import internal.TypeRegistry

/**
 * Register a new type class.
 */
fun <T: Object> registerTypeClass(
    typeName: String,
    superType: KGTypeInfo<*>,
): KGTypeInfo<T> {
    return TypeRegistry.registerType(typeName, superType)
}