package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.SelectionModel
import bindings.gtk.Widget
import bindings.gtk.asSelectionModel
import bindings.gtk.asWidget
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class ViewStack : Widget {
    val adwViewStackPointer get() = gtkWidgetPointer.asTypedPointer<AdwViewStack>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_view_stack_new()!!)

    fun add(
        child: Widget,
        name: String? = null,
        title: String? = null,
        iconName: String? = null
    ): ViewStackPage =
        adw_view_stack_add_titled_with_icon(
            adwViewStackPointer,
            child.gtkWidgetPointer,
            name,
            title,
            iconName
        )!!.asViewStackPage()

    fun getChild(name: String): Widget? = adw_view_stack_get_child_by_name(adwViewStackPointer, name)?.asWidget()

    var hhomogeneous: Boolean
        get() = adw_view_stack_get_hhomogeneous(adwViewStackPointer).boolean
        set(value) = adw_view_stack_set_hhomogeneous(adwViewStackPointer, value.gboolean)

    var vhomogeneous: Boolean
        get() = adw_view_stack_get_vhomogeneous(adwViewStackPointer).boolean
        set(value) = adw_view_stack_set_vhomogeneous(adwViewStackPointer, value.gboolean)

    fun getPage(widget: Widget): ViewStackPage? =
        adw_view_stack_get_page(adwViewStackPointer, widget.gtkWidgetPointer)?.asViewStackPage()

    // TODO the caller needs to free this model
    // TODO can we do a generic variant of the selection model?
    val pages: SelectionModel
        get() = adw_view_stack_get_pages(adwViewStackPointer)!!.asSelectionModel()

    var visibleChild: Widget?
        get() = adw_view_stack_get_visible_child(adwViewStackPointer)?.asWidget()
        set(value) = adw_view_stack_set_visible_child(adwViewStackPointer, value?.gtkWidgetPointer)

    var visibleChildName: String?
        get() = adw_view_stack_get_visible_child_name(adwViewStackPointer)?.toKString()
        set(value) = adw_view_stack_set_visible_child_name(adwViewStackPointer, value)

    fun remove(child: Widget) = adw_view_stack_remove(adwViewStackPointer, child.gtkWidgetPointer)

}