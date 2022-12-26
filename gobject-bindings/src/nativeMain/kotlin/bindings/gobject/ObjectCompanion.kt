package bindings.gobject

import internal.KGTypeInfo
import kotlinx.cinterop.CPointer
import native.gobject.GType

open class ObjectCompanion<T: Object>(open val typeInfo: KGTypeInfo<T>) {
    val gType: GType get() = typeInfo.gType

    fun newInstancePointer(): CPointer<*> {
        return typeInfo.newInstancePointer()
    }

    fun fromPointer(pointer: CPointer<*>): T = fromPointerOrNull(pointer)!!

    fun fromPointerOrNull(pointer: CPointer<*>): T? = typeInfo.instanceFromPointerOrNull(pointer)
}