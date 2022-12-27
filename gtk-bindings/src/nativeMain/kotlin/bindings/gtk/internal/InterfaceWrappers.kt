package bindings.gtk.internal

import bindings.gobject.asTypedPointer
import bindings.gtk.*
import kotlinx.cinterop.CPointer
import native.gtk.*

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

internal class SelectionModelWrapper(
    override val gtkSelectionModelPointer: CPointer<GtkSelectionModel>
) : SelectionModel
