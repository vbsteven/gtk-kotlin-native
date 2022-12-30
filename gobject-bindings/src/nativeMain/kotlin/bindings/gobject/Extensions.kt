package bindings.gobject

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import native.gobject.*

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
inline fun <T : CPointed> gpointer.asTypedPointer() = this as CPointer<T>


fun gpointer.asObject(): Object = Object(this)

fun CPointer<GBinding>.asBinding(): Binding = Binding(this)
fun CPointer<GObject>.asObject(): Object = Object(this)

fun CPointer<GParamSpec>.asParamSpec(): ParamSpec = ParamSpec(this)
fun CPointer<GParamSpecString>.asParamSpecString(): ParamSpecString = ParamSpecString(this)
