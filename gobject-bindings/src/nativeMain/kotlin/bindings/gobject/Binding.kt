package bindings.gobject

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gobject.*

class Binding : Object {
    val gBindingPointer get() = gPointer.asTypedPointer<GBinding>()

    constructor(pointer: CPointer<*>) : super(pointer)

    val flags: GBindingFlags
        get() = g_binding_get_flags(gBindingPointer)

    val source: Object?
        get() = g_binding_get_source(gBindingPointer)?.asObject()

    val sourceProperty: String?
        get() = g_binding_get_source_property(gBindingPointer)?.toKString()

    val target: Object?
        get() = g_binding_get_source(gBindingPointer)?.asObject()

    val targetProperty: String?
        get() = g_binding_get_source_property(gBindingPointer)?.toKString()

    fun unbind() = g_binding_unbind(gBindingPointer)

}