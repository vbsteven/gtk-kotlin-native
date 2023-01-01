package bindings.adw

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class LeafletPage : Object {
    val adwLeafletPagePointer get() = gPointer.asTypedPointer<AdwLeafletPage>()

    constructor(pointer: CPointer<*>) : super(pointer)

    val child: Widget
        get() = adw_leaflet_page_get_child(adwLeafletPagePointer)!!.asWidget()

    var name: String?
        get() = adw_leaflet_page_get_name(adwLeafletPagePointer)?.toKString()
        set(value) = adw_leaflet_page_set_name(adwLeafletPagePointer, value)

    var navigatable: Boolean
        get() = adw_leaflet_page_get_navigatable(adwLeafletPagePointer).boolean
        set(value) = adw_leaflet_page_set_navigatable(adwLeafletPagePointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_LEAFLET_PAGE, ::LeafletPage)
    }
}