package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gtk.testutils.GtkTestBase
import usertypes.registerTypeClass
import kotlinx.cinterop.CPointer
import kotlin.test.Ignore
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

    companion object : ObjectCompanion<MyGtkApplication>(MyGtkApplicationTypeInfo)

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : super("io.quantus.MyTestApplication") {
        onActivate {
            println("MyGtkApplication was activated")
            applicationWasActivated = true
            quit()
        }
    }

}

val MyGtkApplicationTypeInfo = registerTypeClass<MyGtkApplication>("MyGtkApplication", Application.typeInfo)