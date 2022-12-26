package internal.gtk

import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import native.gobject.GClosureNotify
import native.gobject.gpointer

/**
 * Helper function that can be used as a destroy_data handler
 * when connecting to a signal.
 *
 * This implementation assumes [data] is a pointer to a StableRef
 * and calls dispose on it.
 */
internal val staticStableRefDestroy: GClosureNotify =
    staticCFunction { data: gpointer?,
                      _: gpointer? ->
        data?.asStableRef<Any>()?.dispose()
        Unit
    }.reinterpret()