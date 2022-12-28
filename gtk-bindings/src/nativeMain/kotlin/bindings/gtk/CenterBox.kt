package bindings.gtk

import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gtk.*

class CenterBox : Widget {
    val gtkCenterBoxPointer get() = gtkWidgetPointer.asTypedPointer<GtkCenterBox>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_center_box_new()!!)

    var baselinePosition: GtkBaselinePosition
        get() = gtk_center_box_get_baseline_position(gtkCenterBoxPointer)
        set(value) = gtk_center_box_set_baseline_position(gtkCenterBoxPointer, value)

    var centerWidget: Widget?
        get() = gtk_center_box_get_center_widget(gtkCenterBoxPointer)?.asWidget()
        set(value) = gtk_center_box_set_center_widget(gtkCenterBoxPointer, value?.gtkWidgetPointer)

    var startWidget: Widget?
        get() = gtk_center_box_get_start_widget(gtkCenterBoxPointer)?.asWidget()
        set(value) = gtk_center_box_set_start_widget(gtkCenterBoxPointer, value?.gtkWidgetPointer)

    var endWidget: Widget?
        get() = gtk_center_box_get_end_widget(gtkCenterBoxPointer)?.asWidget()
        set(value) = gtk_center_box_set_end_widget(gtkCenterBoxPointer, value?.gtkWidgetPointer)
}