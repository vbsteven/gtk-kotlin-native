package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.GtkNative
import native.gtk.GtkRoot
import native.gtk.gtk_root_get_focus
import native.gtk.gtk_root_set_focus

interface Root : Native {
    val gtkRootPointer: CPointer<GtkRoot> get() = gtkNativePointer.asTypedPointer()

    var focus: Widget?
        get() = gtk_root_get_focus(gtkRootPointer)?.asWidget()
        set(value) = gtk_root_set_focus(gtkRootPointer, value?.widgetPointer)
}

fun CPointer<GtkRoot>.asRoot(): Root = RootWrapper(this)

private class RootWrapper(
    override val gtkRootPointer: CPointer<GtkRoot>
) : Root {
    override val gtkNativePointer: CPointer<GtkNative> get() = gtkRootPointer.asTypedPointer()
}