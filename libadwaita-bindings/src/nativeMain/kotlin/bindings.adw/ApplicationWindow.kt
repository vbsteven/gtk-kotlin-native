package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Application
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.*
import bindings.gtk.ApplicationWindow as GtkApplicationWindow

open class ApplicationWindow : GtkApplicationWindow {

    val adwApplicationWindowPointer get() = gtkWindowPointer.asTypedPointer<AdwApplicationWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(application: Application) : this(adw_application_window_new(application.gtkApplicationPointer)!!)

    var content: Widget?
        get() = adw_application_window_get_content(adwApplicationWindowPointer)?.asWidget()
        set(value) = adw_application_window_set_content(adwApplicationWindowPointer, value?.gtkWidgetPointer)

    override var child = this.content

    companion object {
        val typeInfo = BuiltinTypeInfo(
            "AdwApplicationWindow",
            ADW_TYPE_APPLICATION_WINDOW,
            sizeOf<AdwApplicationWindowClass>(),
            sizeOf<AdwApplicationWindow>(),
            ::ApplicationWindow
        )
    }
}
