package bindings.glib

import kotlinx.cinterop.CPointer
import native.gobject.GVariantType

class VariantType(
    val variantTypePointer: CPointer<GVariantType>
) {
    // TODO implement
}

fun CPointer<GVariantType>.asVariantType(): VariantType = VariantType(this)
