package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.boolean
import bindings.gobject.gboolean
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gtk.*

class Window : Widget {
    @Suppress("UNCHECKED_CAST")
    val gtkWindowPointer get() = gPointer as GtkWindow_autoptr

    constructor() : super(gtk_window_new()!!)
    constructor(pointer: CPointer<*>) : super(pointer)

    var application: Application?
        get() = gtk_window_get_application(gtkWindowPointer)?.asApplication()
        set(value) = gtk_window_set_application(gtkWindowPointer, value?.gtkApplicationPointer)

    var child: Widget?
        get() = gtk_window_get_child(gtkWindowPointer)?.asWidget()
        set(value) = gtk_window_set_child(gtkWindowPointer, value?.widgetPointer)

    var title: String?
        get() = gtk_window_get_title(gtkWindowPointer)?.toKString()
        set(value) = gtk_window_set_title(gtkWindowPointer, value)

    var isDecorated: Boolean
        get() = gtk_window_get_decorated(gtkWindowPointer).boolean
        set(value) = gtk_window_set_decorated(gtkWindowPointer, value.gboolean)

    var defaultSize: Pair<Int, Int>
        get() {
            return memScoped {
                val width = alloc<IntVar>()
                val height = alloc<IntVar>()
                gtk_window_get_default_size(gPointer.reinterpret(), width.ptr, height.ptr)
                Pair(width.value, height.value)
            }
        }
        set(value) {
            gtk_window_set_default_size(gPointer.reinterpret(), value.first, value.second)
        }

    var defaultWidget: Widget?
        get() = gtk_window_get_default_widget(gtkWindowPointer)?.asWidget()
        set(value) = gtk_window_set_default_widget(gtkWindowPointer, value?.widgetPointer)

    var isDeletable: Boolean
        get() = gtk_window_get_deletable(gtkWindowPointer).boolean
        set(value) = gtk_window_set_deletable(gtkWindowPointer, value.gboolean)

    var isDestroyWithParent: Boolean
        get() = gtk_window_get_destroy_with_parent(gtkWindowPointer).boolean
        set(value) = gtk_window_set_destroy_with_parent(gtkWindowPointer, value.gboolean)

    var focus: Widget?
        get() = gtk_window_get_focus(gtkWindowPointer)?.asWidget()
        set(value) = gtk_window_set_focus(gtkWindowPointer, value?.widgetPointer)

    var isFocusVisible: Boolean
        get() = gtk_window_get_focus_visible(gtkWindowPointer).boolean
        set(value) = gtk_window_set_focus_visible(gtkWindowPointer, value.gboolean)

    var isHandleMenubarAccel: Boolean
        get() = gtk_window_get_handle_menubar_accel(gtkWindowPointer).boolean
        set(value) = gtk_window_set_handle_menubar_accel(gtkWindowPointer, value.gboolean)

    val isHideOnClose: Boolean
        get() = gtk_window_get_hide_on_close(gtkWindowPointer).boolean

    var iconName: String?
        get() = gtk_window_get_icon_name(gtkWindowPointer)?.toKString()
        set(value) = gtk_window_set_icon_name(gtkWindowPointer, value)

    var isMnemonicsVisible: Boolean
        get() = gtk_window_get_mnemonics_visible(gtkWindowPointer).boolean
        set(value) = gtk_window_set_mnemonics_visible(gtkWindowPointer, value.gboolean)

    var isModal: Boolean
        get() = gtk_window_get_modal(gtkWindowPointer).boolean
        set(value) = gtk_window_set_modal(gtkWindowPointer, value.gboolean)

    var isResizable: Boolean
        get() = gtk_window_get_resizable(gtkWindowPointer).boolean
        set(value) = gtk_window_set_resizable(gtkWindowPointer, value.gboolean)

    var titleBar: Widget?
        get() = gtk_window_get_titlebar(gtkWindowPointer)?.asWidget()
        set(value) = gtk_window_set_titlebar(gtkWindowPointer, value?.widgetPointer)

    var transientFor: Window?
        get() = gtk_window_get_transient_for(gtkWindowPointer)?.asWindow()
        set(value) = gtk_window_set_transient_for(gtkWindowPointer, value?.gtkWindowPointer)

    val hasGroup: Boolean get() = gtk_window_has_group(gtkWindowPointer).boolean
    val isActive: Boolean get() = gtk_window_is_active(gtkWindowPointer).boolean
    val isFullscreen: Boolean get() = gtk_window_is_fullscreen(gtkWindowPointer).boolean
    val isMaximized: Boolean get() = gtk_window_is_maximized(gtkWindowPointer).boolean

    fun close() = gtk_window_close(gtkWindowPointer)
    fun fullscreen() = gtk_window_fullscreen(gtkWindowPointer)
    fun maximize() = gtk_window_maximize(gtkWindowPointer)
    fun minimize() = gtk_window_minimize(gtkWindowPointer)

    fun present() = gtk_window_present(gtkWindowPointer)

    fun setStartupId(startupId: String) = gtk_window_set_startup_id(gtkWindowPointer, startupId)

    fun unfullscreen() = gtk_window_unfullscreen(gtkWindowPointer)
    fun unmaximize() = gtk_window_unmaximize(gtkWindowPointer)
    fun unminimize() = gtk_window_unminimize(gtkWindowPointer)

    companion object : ObjectCompanion<Window>(WindowTypeInfo) {
        var defaultIconName: String?
            get() = gtk_window_get_default_icon_name()?.toKString()
            set(value) = gtk_window_set_default_icon_name(value)

        fun setInteractiveDebugging(enable: Boolean) = gtk_window_set_interactive_debugging(enable.gboolean)
        fun setAutoStartupNotification(setting: Boolean) = gtk_window_set_auto_startup_notification(setting.gboolean)

        // TODO getTopLevels
        // TODO listToplevels
    }
}

val WindowTypeInfo = BuiltinTypeInfo<Window>(
    "GtkWindow",
    GTK_TYPE_WINDOW,
    sizeOf<GtkWindowClass>(),
    sizeOf<GtkWindow>(),
    ::Window
)

fun CPointer<GtkWindow>.asWindow(): Window = Window(this)