import bindings.gobject.*
import kotlinx.cinterop.reinterpret
import native.gobject.g_type_check_instance_is_a
import usertypes.registerTypeClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CustomObjectTest {

    @Test
    fun testCustomObjectCreation() {
        val o = MyCustomObject()
        assertEquals("", o.name)
        assertTrue(g_type_check_instance_is_a(o.gPointer.reinterpret(), MyCustomObject.gType).boolean)
        assertTrue(g_type_check_instance_is_a(o.gPointer.reinterpret(), Object.gType).boolean)
        assertFalse(g_type_check_instance_is_a(o.gPointer.reinterpret(), AnotherClass.gType).boolean)
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

        val anotherObject = MyCustomObject.fromPointer(pointer)
        assertEquals("Steven", anotherObject.name)
    }

}

class MyCustomObject(
    val name: String = ""
) : Object(MyCustomObject.newInstancePointer()) {

    companion object : ObjectCompanion<MyCustomObject>(MyCustomObjectTypeInfo)
}

val MyCustomObjectTypeInfo = registerTypeClass<MyCustomObject>(
    "MyCustomObject",
    Object.typeInfo,
)

class AnotherClass : Object() {
    companion object : ObjectCompanion<AnotherClass>(AnotherCustomObjectTypeInfo)
}

val AnotherCustomObjectTypeInfo = registerTypeClass<AnotherClass>(
    "AnotherClass",
    Object.typeInfo,
)