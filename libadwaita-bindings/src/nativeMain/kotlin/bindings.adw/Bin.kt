package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import kotlinx.cinterop.CPointer
import native.adwaita.AdwBin
import native.adwaita.adw_bin_get_child
import native.adwaita.adw_bin_new
import native.adwaita.adw_bin_set_child

class Bin : Widget {
    val adwBinPointer get() = gtkWidgetPointer.asTypedPointer<AdwBin>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_bin_new()!!)

    var child: Widget?
        get() = adw_bin_get_child(adwBinPointer)?.asWidget()
        set(value) = adw_bin_set_child(adwBinPointer, value?.gtkWidgetPointer)
}