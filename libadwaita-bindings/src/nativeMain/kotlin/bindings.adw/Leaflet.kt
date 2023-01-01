package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.SelectionModel
import bindings.gtk.Widget
import bindings.gtk.asSelectionModel
import bindings.gtk.asWidget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.adwaita.*

class Leaflet : Widget {
    val adwLeafletPointer get() = gtkWidgetPointer.asTypedPointer<AdwLeaflet>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_leaflet_new()!!)

    fun append(child: Widget): LeafletPage =
        adw_leaflet_append(adwLeafletPointer, child.gtkWidgetPointer)!!.asLeafletPage()

    fun getAdjacentChild(direction: AdwNavigationDirection) =
        adw_leaflet_get_adjacent_child(adwLeafletPointer, direction)?.asWidget()

    var canNavigateBack: Boolean
        get() = adw_leaflet_get_can_navigate_back(adwLeafletPointer).boolean
        set(value) = adw_leaflet_set_can_navigate_back(adwLeafletPointer, value.gboolean)

    var canNavigateForward: Boolean
        get() = adw_leaflet_get_can_navigate_forward(adwLeafletPointer).boolean
        set(value) = adw_leaflet_set_can_navigate_forward(adwLeafletPointer, value.gboolean)

    var canUnfold: Boolean
        get() = adw_leaflet_get_can_unfold(adwLeafletPointer).boolean
        set(value) = adw_leaflet_set_can_unfold(adwLeafletPointer, value.gboolean)

    fun getChildByName(name: String): Widget? = adw_leaflet_get_child_by_name(adwLeafletPointer, name)?.asWidget()

    // TODO child transition params with AdwSpringParams

    val isChildTransitionRunning: Boolean
        get() = adw_leaflet_get_child_transition_running(adwLeafletPointer).boolean

    var foldThresholdPolicy: AdwFoldThresholdPolicy
        get() = adw_leaflet_get_fold_threshold_policy(adwLeafletPointer)
        set(value) = adw_leaflet_set_fold_threshold_policy(adwLeafletPointer, value)

    val isFolded: Boolean
        get() = adw_leaflet_get_folded(adwLeafletPointer).boolean

    var homogeneous: Boolean
        get() = adw_leaflet_get_homogeneous(adwLeafletPointer).boolean
        set(value) = adw_leaflet_set_homogeneous(adwLeafletPointer, value.gboolean)

    var modeTransitionDuration: Int
        get() = adw_leaflet_get_mode_transition_duration(adwLeafletPointer).toInt()
        set(value) = adw_leaflet_set_mode_transition_duration(adwLeafletPointer, value.toUInt())

    fun getPage(child: Widget): LeafletPage? =
        adw_leaflet_get_page(adwLeafletPointer, child.gtkWidgetPointer)?.asLeafletPage()

    /**
     * TODO calling this takes ownership and data needs to be freed
     */
    val pages: SelectionModel
        get() = adw_leaflet_get_pages(adwLeafletPointer)!!.asSelectionModel()

    var transitionType: AdwLeafletTransitionType
        get() = adw_leaflet_get_transition_type(adwLeafletPointer)
        set(value) = adw_leaflet_set_transition_type(adwLeafletPointer, value)

    var visibleChild: Widget?
        get() = adw_leaflet_get_visible_child(adwLeafletPointer)?.asWidget()
        set(value) = adw_leaflet_set_visible_child(adwLeafletPointer, value?.gtkWidgetPointer)

    var visibleChildName: String?
        get() = adw_leaflet_get_visible_child_name(adwLeafletPointer)?.toKString()
        set(value) = adw_leaflet_set_visible_child_name(adwLeafletPointer, value)

    fun insertChildAfter(child: Widget, sibling: Widget): LeafletPage = adw_leaflet_insert_child_after(
        adwLeafletPointer,
        child.gtkWidgetPointer,
        sibling.gtkWidgetPointer
    )!!.asLeafletPage()

    fun navigate(direction: AdwNavigationDirection): Boolean =
        adw_leaflet_navigate(adwLeafletPointer, direction).boolean

    fun prepend(child: Widget): LeafletPage =
        adw_leaflet_prepend(adwLeafletPointer, child.gtkWidgetPointer)!!.asLeafletPage()

    fun remove(child: Widget) = adw_leaflet_remove(adwLeafletPointer, child.gtkWidgetPointer)

    fun reorderChildAfter(child: Widget, sibling: Widget) =
        adw_leaflet_reorder_child_after(adwLeafletPointer, child.gtkWidgetPointer, sibling.gtkWidgetPointer)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_LEAFLET, ::Leaflet)
    }
}