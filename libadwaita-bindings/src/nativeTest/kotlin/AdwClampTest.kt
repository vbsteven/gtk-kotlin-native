import bindings.adw.Clamp
import bindings.gtk.Button
import kotlin.test.Test

class AdwClampTest : AdwTestBase() {

    @Test
    fun testAdwClamp() = runApplicationWindow { window ->
        val clamp = Clamp()

        clamp.child = Button("Hello")
        clamp.maximumSize = 200

        window.content = clamp
    }
}