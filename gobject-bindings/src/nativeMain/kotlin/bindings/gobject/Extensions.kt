package bindings.gobject

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import native.gobject.gboolean
import native.gobject.gpointer

/*
 * Utility extensions for converting native types to Kotlin types and back.
 */

inline val gboolean.boolean: Boolean
    get() = this > 0

inline val Boolean.gboolean: gboolean
    get() = if (this) 1 else 0

/**
 * Convert a generic [gpointer] into a [CPointer] of the given type.
 */
@Suppress("UNCHECKED_CAST")
inline fun <T: CPointed> gpointer.asTypedPointer() = this as CPointer<T>