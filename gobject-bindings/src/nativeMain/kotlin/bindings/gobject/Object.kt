package bindings.gobject

import kotlinx.cinterop.CPointer
import native.gobject.GType
import native.gobject.G_TYPE_OBJECT
import native.gobject.g_object_new

open class Object(pointer: CPointer<*>) {
    val gPointer: CPointer<*> = pointer

    constructor() : this(newInstancePointer())

    companion object : KGType() {
        override val gType: GType = G_TYPE_OBJECT
        override fun newInstancePointer(): CPointer<*> {
            return g_object_new(G_TYPE_OBJECT, null)!!
        }
    }

}

abstract class KGType {
    abstract val gType: GType
    abstract fun newInstancePointer(): CPointer<*>
}

