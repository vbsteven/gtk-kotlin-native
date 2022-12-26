package bindings.gtk

import kotlinx.cinterop.*
import native.gtk.GtkNative
import native.gtk.gtk_native_get_surface_transform

interface Native {
    val gtkNativePointer: CPointer<GtkNative>

    fun getSurfaceTransform(): Coordinate = memScoped {
        val x = alloc<DoubleVar>()
        val y = alloc<DoubleVar>()
        gtk_native_get_surface_transform(gtkNativePointer, x.ptr, y.ptr)
        return Coordinate(x.value, y.value)
    }

    data class Coordinate(val x: Double, val y: Double)
}

fun CPointer<GtkNative>.asNative(): Native = NativeWrapper(this)

private class NativeWrapper(
    override val gtkNativePointer: CPointer<GtkNative>
) : Native