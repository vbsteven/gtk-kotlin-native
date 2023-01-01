package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.*
import bindings.gtk.Window as GtkWindow

open class Window : GtkWindow {

    val adwWindowPointer get() = gtkWindowPointer.asTypedPointer<AdwWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_window_new()!!)

    var content: Widget?
        get() = adw_window_get_content(adwWindowPointer)?.asWidget()
        set(value) = adw_window_set_content(adwWindowPointer, value?.gtkWidgetPointer)

    override var child = this.content

    companion object {
        val Type = BuiltinTypeInfo(
            ADW_TYPE_WINDOW,
            sizeOf<AdwWindowClass>(),
            sizeOf<AdwWindow>(),
            ::Window
        )
    }
}