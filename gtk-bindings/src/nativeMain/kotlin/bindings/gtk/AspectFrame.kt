package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import kotlinx.cinterop.CPointer
import native.gtk.*

class AspectFrame : Widget {
    val gtkAspectFramePointer get() = gtkWidgetPointer.asTypedPointer<GtkAspectFrame>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(
        xalign: Float,
        yalign: Float,
        ratio: Float,
        obeyChild: Boolean
    ) : this(gtk_aspect_frame_new(xalign, yalign, ratio, obeyChild.gboolean)!!)

    var child: Widget?
        get() = gtk_aspect_frame_get_child(gtkAspectFramePointer)?.asWidget()
        set(value) = gtk_aspect_frame_set_child(gtkAspectFramePointer, value?.gtkWidgetPointer)

    var obeyChild: Boolean
        get() = gtk_aspect_frame_get_obey_child(gtkAspectFramePointer).boolean
        set(value) = gtk_aspect_frame_set_obey_child(gtkAspectFramePointer, value.gboolean)

    var ratio: Float
        get() = gtk_aspect_frame_get_ratio(gtkAspectFramePointer)
        set(value) = gtk_aspect_frame_set_ratio(gtkAspectFramePointer, value)

    var xalign: Float
        get() = gtk_aspect_frame_get_xalign(gtkAspectFramePointer)
        set(value) = gtk_aspect_frame_set_xalign(gtkAspectFramePointer, value)

    var yalign: Float
        get() = gtk_aspect_frame_get_yalign(gtkAspectFramePointer)
        set(value) = gtk_aspect_frame_set_yalign(gtkAspectFramePointer, value)

}