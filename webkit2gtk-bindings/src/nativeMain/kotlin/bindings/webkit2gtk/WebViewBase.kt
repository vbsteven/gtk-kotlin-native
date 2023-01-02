package bindings.webkit2gtk

import bindings.gobject.asTypedPointer
import bindings.gtk.Widget
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.gtk.GtkWidget
import native.webkit2gtk.WEBKIT_TYPE_WEB_VIEW
import native.webkit2gtk.WebKitWebViewBase
import native.webkit2gtk.WebKitWebViewBaseClass

open class WebViewBase : Widget {
    val webkitWebKitWebViewBasePointer get() = gtkWidgetPointer.asTypedPointer<GtkWidget>()

    constructor(pointer: CPointer<*>) : super(pointer)

    companion object {
        val Type = BuiltinTypeInfo(
            WEBKIT_TYPE_WEB_VIEW,
            sizeOf<WebKitWebViewBaseClass>(),
            sizeOf<WebKitWebViewBase>(),
            ::WebViewBase
        )
    }
}