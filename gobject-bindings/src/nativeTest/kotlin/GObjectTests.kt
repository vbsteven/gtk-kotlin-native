import kotlin.test.Test

class GObjectTests {
    @Test
    fun test() {
        println("Creating new gobject")
        val obj = bindings.gobject.Object()
        println(obj)
        println(obj.gPointer)
    }
}