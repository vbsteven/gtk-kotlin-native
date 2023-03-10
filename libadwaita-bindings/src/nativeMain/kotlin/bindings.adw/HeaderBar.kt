package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class HeaderBar : Widget {

    val adwHeaderBarPointer get() = gtkWidgetPointer.asTypedPointer<AdwHeaderBar>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_header_bar_new()!!)

    var showEndTitleButtons: Boolean
        get() = adw_header_bar_get_show_end_title_buttons(adwHeaderBarPointer).boolean
        set(value) = adw_header_bar_set_show_end_title_buttons(adwHeaderBarPointer, value.gboolean)

    var showStartTitleButtons: Boolean
        get() = adw_header_bar_get_show_start_title_buttons(adwHeaderBarPointer).boolean
        set(value) = adw_header_bar_set_show_start_title_buttons(adwHeaderBarPointer, value.gboolean)

    var centerPolicy: AdwCenteringPolicy
        get() = adw_header_bar_get_centering_policy(adwHeaderBarPointer)
        set(value) = adw_header_bar_set_centering_policy(adwHeaderBarPointer, value)

    var decorationLayout: String?
        get() = adw_header_bar_get_decoration_layout(adwHeaderBarPointer)?.toKString()
        set(value) = adw_header_bar_set_decoration_layout(adwHeaderBarPointer, value)

    var titleWidget: Widget?
        get() = adw_header_bar_get_title_widget(adwHeaderBarPointer)?.asWidget()
        set(value) = adw_header_bar_set_title_widget(adwHeaderBarPointer, value?.gtkWidgetPointer)

    fun packStart(widget: Widget) = adw_header_bar_pack_start(adwHeaderBarPointer, widget.gtkWidgetPointer)

    fun packEnd(widget: Widget) = adw_header_bar_pack_end(adwHeaderBarPointer, widget.gtkWidgetPointer)

    fun remove(widget: Widget) = adw_header_bar_remove(adwHeaderBarPointer, widget.gtkWidgetPointer)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_HEADER_BAR, ::HeaderBar)
    }
}