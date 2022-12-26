package bindings.glib

import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import native.gobject.*

class Variant(
    val variantPointer: CPointer<GVariant>
) {
    constructor(value: Boolean) : this(g_variant_new_boolean(value.gboolean)!!)
    constructor(value: Int): this(g_variant_new_int32(value)!!)
    constructor(value: UInt) : this(g_variant_new_uint32(value)!!)
    constructor(value: Long): this(g_variant_new_int64(value)!!)
    constructor(value: ULong): this(g_variant_new_uint64(value)!!)
    constructor(value: String) : this(g_variant_new_string(value)!!)
}

fun CPointer<GVariant>.asVariant(): Variant = Variant(this)