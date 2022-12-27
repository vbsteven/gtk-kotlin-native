package bindings.gobject

import internal.BuiltinTypeInfo
import internal.TypeRegistry
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.gobject.GObject
import native.gobject.G_TYPE_OBJECT
import native.gobject.g_type_name_from_instance
import native.gobject.gpointer


open class Object(pointer: CPointer<*>) {
    val gPointer: CPointer<*> = pointer

    constructor() : this(typeInfo.newInstancePointer())

    init {
        associateCustomObject()
    }

    companion object : ObjectCompanion<Object>(ObjectTypeInfo)

    private fun associateCustomObject() {
        // TODO is there a better way to get the type from an object?
        val typeInfo = g_type_name_from_instance(gPointer.reinterpret())
            ?.toKString()
            ?.let { TypeRegistry.getTypeInfoForName(it) }

        typeInfo?.associate(this, this.gPointer)
    }
}

val ObjectTypeInfo = BuiltinTypeInfo<Object>(
    "GObject",
    G_TYPE_OBJECT,
    sizeOf<native.gobject.GObjectClass>(),
    sizeOf<native.gobject.GObject>(),
    ::Object
)

fun CPointer<GObject>.asObject(): Object = Object(this)
fun gpointer.asObject(): Object = Object(this)


