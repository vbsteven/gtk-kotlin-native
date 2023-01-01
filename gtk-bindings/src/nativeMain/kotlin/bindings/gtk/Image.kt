package bindings.gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import native.gtk.*

class Image : Widget {
    val gtkImagePointer get() = gtkWidgetPointer.asTypedPointer<GtkImage>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_image_new()!!)

    fun clear() = gtk_image_clear(gtkImagePointer)


    var iconName: String?
        get() = gtk_image_get_icon_name(gtkImagePointer)?.toKString()
        set(value) = gtk_image_set_from_icon_name(gtkImagePointer, value)

    fun setResourcePath(resourcePath: String) = gtk_image_set_from_resource(gtkImagePointer, resourcePath)

    var iconSize: GtkIconSize
        get() = gtk_image_get_icon_size(gtkImagePointer)
        set(value) = gtk_image_set_icon_size(gtkImagePointer, value)

    var pixelSize: Int
        get() = gtk_image_get_pixel_size(gtkImagePointer)
        set(value) = gtk_image_set_pixel_size(gtkImagePointer, value)

    val storageType: GtkImageType = gtk_image_get_storage_type(gtkImagePointer)

    // TODO add gicon property and constructor
    // TODO add paintable property and constructor
    // TODO add pixbuf property

    companion object {
        fun newFromFile(fileName: String) = Image(gtk_image_new_from_file(fileName)!!)
        fun newFromIconName(iconName: String) = Image(gtk_image_new_from_icon_name(iconName)!!)
        fun newFromResource(resourcePath: String) = Image(gtk_image_new_from_resource(resourcePath)!!)

        val Type = BuiltinTypeInfo(GTK_TYPE_IMAGE, ::Image)
    }

}