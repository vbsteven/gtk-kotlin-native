import bindings.gtk.Application
import bindings.gtk.ApplicationWindow
import bindings.webkit2gtk.WebView
import kotlin.test.Test

class WebViewTest {
    @Test
    fun testWebView() {
        val app = Application("io.quantus.webkit2gtk")
        app.onActivate {
            val window = ApplicationWindow(app)
            window.defaultSize = Pair(1280, 720)

            val webView = WebView()
            webView.loadUri("https://github.com/vbsteven/gtk-kotlin-native")

            window.child = webView
            window.show()
        }
        app.run()
        app.unref()
    }
}