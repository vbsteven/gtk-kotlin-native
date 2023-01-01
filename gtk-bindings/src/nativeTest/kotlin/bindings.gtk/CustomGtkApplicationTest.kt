package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gtk.testutils.GtkTestBase
import kotlinx.cinterop.CPointer
import kotlin.test.Test
import kotlin.test.assertTrue

class CustomGtkApplicationTest : GtkTestBase() {
    @Test
    fun createCustomGtkApplication() {
        val myApplication = MyGtkApplication()
        myApplication.runApplication()
        assertTrue(myApplication.applicationWasActivated, "Application was not activated")
    }
}

class MyGtkApplication : Application {

    var applicationWasActivated = false

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : super("io.quantus.MyTestApplication") {
        onActivate {
            println("MyGtkApplication was activated")
            applicationWasActivated = true
            quit()
        }
    }

    companion object : ObjectCompanion<MyGtkApplication>() {
        override val typeName = "MyGtkApplication"
        override val parentType = Application.Type
    }
}
