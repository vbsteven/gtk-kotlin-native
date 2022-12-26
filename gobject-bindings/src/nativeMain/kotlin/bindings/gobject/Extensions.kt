package bindings.gobject

import native.gobject.gboolean

/*
 * Utility extensions for converting native types to Kotlin types and back.
 */

val gboolean.boolean: Boolean
    get() = this > 0