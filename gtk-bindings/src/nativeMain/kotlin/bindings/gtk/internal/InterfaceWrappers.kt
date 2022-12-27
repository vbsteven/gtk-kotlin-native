package bindings.gtk.internal

import bindings.gobject.asTypedPointer
import bindings.gtk.Actionable
import bindings.gtk.Native
import bindings.gtk.Orientable
import bindings.gtk.Root
import kotlinx.cinterop.CPointer
import native.gtk.GtkActionable
import native.gtk.GtkNative
import native.gtk.GtkOrientable
import native.gtk.GtkRoot

internal class ActionableWrapper(
    override val gtkActionablePointer: CPointer<GtkActionable>
) : Actionable

internal class NativeWrapper(
    override val gtkNativePointer: CPointer<GtkNative>
) : Native

internal class OrientableWrapper(
    override val gtkOrientablePointer: CPointer<GtkOrientable>
) : Orientable

internal class RootWrapper(
    override val gtkRootPointer: CPointer<GtkRoot>
) : Root {
    override val gtkNativePointer: CPointer<GtkNative> get() = gtkRootPointer.asTypedPointer()
}