package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.Widget
import bindings.gtk.asWidget
import kotlinx.cinterop.CPointer
import native.adwaita.*
import native.gtk.GtkPackType

class Flap : Widget {
    val adwFlapPointer get() = gtkWidgetPointer.asTypedPointer<AdwFlap>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_flap_new()!!)

    var content: Widget?
        get() = adw_flap_get_content(adwFlapPointer)?.asWidget()
        set(value) = adw_flap_set_content(adwFlapPointer, value?.gtkWidgetPointer)

    var flap: Widget?
        get() = adw_flap_get_flap(adwFlapPointer)?.asWidget()
        set(value) = adw_flap_set_flap(adwFlapPointer, value?.gtkWidgetPointer)

    var flapPosition: GtkPackType
        get() = adw_flap_get_flap_position(adwFlapPointer)
        set(value) = adw_flap_set_flap_position(adwFlapPointer, value)

    var foldDuration: Int
        get() = adw_flap_get_fold_duration(adwFlapPointer).toInt()
        set(value) = adw_flap_set_fold_duration(adwFlapPointer, value.toUInt())

    var foldPolicy: AdwFlapFoldPolicy
        get() = adw_flap_get_fold_policy(adwFlapPointer)
        set(value) = adw_flap_set_fold_policy(adwFlapPointer, value)

    var foldThresholdPolicy: AdwFoldThresholdPolicy
        get() = adw_flap_get_fold_threshold_policy(adwFlapPointer)
        set(value) = adw_flap_set_fold_threshold_policy(adwFlapPointer, value)

    val isFolded: Boolean
        get() = adw_flap_get_folded(adwFlapPointer).boolean

    var locked: Boolean
        get() = adw_flap_get_locked(adwFlapPointer).boolean
        set(value) = adw_flap_set_locked(adwFlapPointer, value.gboolean)

    var modal: Boolean
        get() = adw_flap_get_modal(adwFlapPointer).boolean
        set(value) = adw_flap_set_modal(adwFlapPointer, value.gboolean)

    var revealFlap: Boolean
        get() = adw_flap_get_reveal_flap(adwFlapPointer).boolean
        set(value) = adw_flap_set_reveal_flap(adwFlapPointer, value.gboolean)

    // TODO reveal params with AdwSpringParams

    val revealProgress: Double
        get() = adw_flap_get_reveal_progress(adwFlapPointer)

    var separator: Widget?
        get() = adw_flap_get_separator(adwFlapPointer)?.asWidget()
        set(value) = adw_flap_set_separator(adwFlapPointer, value?.gtkWidgetPointer)

    var swipeToClose: Boolean
        get() = adw_flap_get_swipe_to_close(adwFlapPointer).boolean
        set(value) = adw_flap_set_swipe_to_close(adwFlapPointer, value.gboolean)

    var swipeToOpen: Boolean
        get() = adw_flap_get_swipe_to_open(adwFlapPointer).boolean
        set(value) = adw_flap_set_swipe_to_open(adwFlapPointer, value.gboolean)

    var transitionType: AdwFlapTransitionType
        get() = adw_flap_get_transition_type(adwFlapPointer)
        set(value) = adw_flap_set_transition_type(adwFlapPointer, value)
}