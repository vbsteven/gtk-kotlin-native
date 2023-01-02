package bindings.webkit2gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.webkit2gtk.*

class WebView : WebViewBase {

    val webkitWebViewPointer get() = gtkWidgetPointer.asTypedPointer<WebKitWebView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(webkit_web_view_new()!!)

    fun loadUri(uri: String) = webkit_web_view_load_uri(webkitWebViewPointer, uri)

    companion object {
        val Type =
            BuiltinTypeInfo(WEBKIT_TYPE_WEB_VIEW, sizeOf<WebKitWebViewClass>(), sizeOf<WebKitWebView>(), ::WebView)
    }
}