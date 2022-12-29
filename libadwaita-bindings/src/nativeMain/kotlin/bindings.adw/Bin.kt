package bindings.adw

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.*

open class Bin : Widget {
    val adwBinPointer get() = gtkWidgetPointer.asTypedPointer<AdwBin>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_bin_new()!!)

    var child: Widget?
        get() = adw_bin_get_child(adwBinPointer)?.asWidget()
        set(value) = adw_bin_set_child(adwBinPointer, value?.gtkWidgetPointer)

    companion object : ObjectCompanion<Bin>(BinTypeInfo)
}

private val BinTypeInfo = BuiltinTypeInfo(
    "AdwBin",
    ADW_TYPE_BIN,
    sizeOf<AdwBinClass>(),
    sizeOf<AdwBin>(),
    ::Bin
)