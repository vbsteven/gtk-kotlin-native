package bindings.gtk

import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gtk.*

class LevelBar : Widget {
    val gtkLevelBarPointer get() = gtkWidgetPointer.asTypedPointer<GtkLevelBar>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(gtk_level_bar_new()!!)
    constructor(minValue: Double, maxValue: Double) : this(gtk_level_bar_new_for_interval(minValue, maxValue)!!)

    var inverted: Boolean
        get() = gtk_level_bar_get_inverted(gtkLevelBarPointer).boolean
        set(value) = gtk_level_bar_set_inverted(gtkLevelBarPointer, value.gboolean)

    var maxValue: Double
        get() = gtk_level_bar_get_max_value(gtkLevelBarPointer)
        set(value) = gtk_level_bar_set_max_value(gtkLevelBarPointer, value)

    var minValue: Double
        get() = gtk_level_bar_get_min_value(gtkLevelBarPointer)
        set(value) = gtk_level_bar_set_min_value(gtkLevelBarPointer, value)

    var mode: GtkLevelBarMode
        get() = gtk_level_bar_get_mode(gtkLevelBarPointer)
        set(value) = gtk_level_bar_set_mode(gtkLevelBarPointer, value)

    var value: Double
        get() = gtk_level_bar_get_value(gtkLevelBarPointer)
        set(value) = gtk_level_bar_set_value(gtkLevelBarPointer, value)

    fun addOffsetValue(name: String, value: Double) = gtk_level_bar_add_offset_value(gtkLevelBarPointer, name, value)

    fun getOffsetValue(name: String): Double? = memScoped {
        val value = alloc<DoubleVar>()
        val success = gtk_level_bar_get_offset_value(gtkLevelBarPointer, name, value.ptr).boolean
        return if (success) value.value else null
    }

    fun removeOffsetValue(name: String) = gtk_level_bar_remove_offset_value(gtkLevelBarPointer, name)

    // TODO offset-changed signal

    companion object {
        val Type = BuiltinTypeInfo(GTK_TYPE_LEVEL_BAR, ::LevelBar)
    }
}