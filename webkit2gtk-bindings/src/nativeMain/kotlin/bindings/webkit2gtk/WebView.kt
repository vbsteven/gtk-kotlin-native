package bindings.webkit2gtk

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKString
import native.webkit2gtk.*

class WebView : WebViewBase {

    val webkitWebViewPointer get() = gtkWidgetPointer.asTypedPointer<WebKitWebView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(webkit_web_view_new()!!)

    fun loadUri(uri: String) = webkit_web_view_load_uri(webkitWebViewPointer, uri)

    fun goBack() = webkit_web_view_go_back(webkitWebViewPointer)
    fun goForward() = webkit_web_view_go_forward(webkitWebViewPointer)
    fun reload() = webkit_web_view_reload(webkitWebViewPointer)

    val title: String? = webkit_web_view_get_title(webkitWebViewPointer)?.toKString()
    val uri: String? = webkit_web_view_get_uri(webkitWebViewPointer)?.toKString()

    companion object {
        val Type =
            BuiltinTypeInfo(WEBKIT_TYPE_WEB_VIEW, sizeOf<WebKitWebViewClass>(), sizeOf<WebKitWebView>(), ::WebView)
    }
}