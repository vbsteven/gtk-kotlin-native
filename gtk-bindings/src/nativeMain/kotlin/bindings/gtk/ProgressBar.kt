package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class ProgressBar : Widget, Orientable {
    val gtkProgressBarPointer get() = gtkWidgetPointer.asTypedPointer<GtkProgressBar>()

    override val gtkOrientablePointer get() = gtkWidgetPointer.asTypedPointer<GtkOrientable>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_progress_bar_new()!!)

    var ellipsize: PangoEllipsizeMode
        get() = gtk_progress_bar_get_ellipsize(gtkProgressBarPointer)
        set(value) = gtk_progress_bar_set_ellipsize(gtkProgressBarPointer, value)

    var fraction: Double
        get() = gtk_progress_bar_get_fraction(gtkProgressBarPointer)
        set(value) = gtk_progress_bar_set_fraction(gtkProgressBarPointer, value)

    var inverted: Boolean
        get() = gtk_progress_bar_get_inverted(gtkProgressBarPointer).boolean
        set(value) = gtk_progress_bar_set_inverted(gtkProgressBarPointer, value.gboolean)

    var pulseStep: Double
        get() = gtk_progress_bar_get_pulse_step(gtkProgressBarPointer)
        set(value) = gtk_progress_bar_set_pulse_step(gtkProgressBarPointer, value)

    var showText: Boolean
        get() = gtk_progress_bar_get_show_text(gtkProgressBarPointer).boolean
        set(value) = gtk_progress_bar_set_show_text(gtkProgressBarPointer, value.gboolean)

    var text: String?
        get() = gtk_progress_bar_get_text(gtkProgressBarPointer)?.toKString()
        set(value) = gtk_progress_bar_set_text(gtkProgressBarPointer, value)

    fun pulse() = gtk_progress_bar_pulse(gtkProgressBarPointer)

}