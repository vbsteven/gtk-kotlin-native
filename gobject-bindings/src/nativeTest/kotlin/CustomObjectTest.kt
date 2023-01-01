import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import bindings.gobject.boolean
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import native.gobject.g_type_check_instance_is_a
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CustomObjectTest {

    @Test
    fun testCustomObjectCreation() {
        val o = MyCustomObject()
        assertEquals("", o.name)
        assertTrue(g_type_check_instance_is_a(o.gPointer.reinterpret(), MyCustomObject.Type.gType).boolean)
        assertTrue(g_type_check_instance_is_a(o.gPointer.reinterpret(), Object.Type.gType).boolean)
        assertFalse(g_type_check_instance_is_a(o.gPointer.reinterpret(), AnotherClass.Type.gType).boolean)
    }

    @Test
    fun testCustomObjectCreationWithName() {
        val o = MyCustomObject("Steven")
        assertEquals("Steven", o.name)
    }

    @Test
    fun testCustomObjectFromPointer() {
        val o = MyCustomObject("Steven")
        assertEquals("Steven", o.name)

        val pointer = o.gPointer

        val anotherObject = MyCustomObject.instanceFromPointer(pointer)
        assertEquals("Steven", anotherObject.name)
    }

}

private class MyCustomObject : Object {
    var name: String = ""

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(newInstancePointer())

    constructor(name: String) : this() {
        this.name = name
    }

    companion object : ObjectCompanion<MyCustomObject>() {
        override val typeName = "MyCustomObject"
        override val parentType = Object.Type
    }
}

private class AnotherClass : Object {
    constructor(pointer: CPointer<*>) : super(pointer)

    companion object : ObjectCompanion<AnotherClass>() {
        override val typeName = "AnotherClass"
        override val parentType = Object.Type
    }
}