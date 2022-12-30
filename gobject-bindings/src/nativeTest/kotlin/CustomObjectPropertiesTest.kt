import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import kotlinx.cinterop.CPointer
import native.gobject.G_BINDING_BIDIRECTIONAL
import usertypes.ObjectClass
import usertypes.intProperty
import usertypes.nullableStringProperty
import usertypes.stringProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomObjectPropertiesTest {

    @Test
    fun testProperties() {
        val obj = MyPerson()
        // set kotlin properties directly
        obj.name = "Value1"
        obj.surname = "Surname1"

        // access them as gobject properties
        assertEquals("Value1", obj.getStringProperty("name"))
        assertEquals("Surname1", obj.getStringProperty("surname"))

        obj.name = "Value2"
        obj.surname = "Surname2"

        // access them as gobject properties
        assertEquals("Value2", obj.getStringProperty("name"))
        assertEquals("Surname2", obj.getStringProperty("surname"))

        // set them as gobject properties
        obj.setProperty("name", "Hello")
        obj.setProperty("surname", "Test")
        assertEquals("Hello", obj.name)
        assertEquals("Test", obj.surname)

        // test nullable property
        obj.setProperty("surname", null)
        assertEquals(null, obj.surname)

        // bind one property to another
        obj.bindProperty("name", obj, "surname")
        // set the source property to something
        obj.setProperty("name", "sync")

        // make sure the target property is changed
        assertEquals("sync", obj.name)
        assertEquals("sync", obj.surname)

        // set the source property from Kotlin
        obj.name = "sync2"
        // make sure the target property is changed
        assertEquals("sync2", obj.name)
        assertEquals("sync2", obj.surname)

    }

    @Test
    fun testMultipleObjects() {
        val o1 = MyPerson()
        assertEquals("", o1.name)
        o1.name = "o1"
        val o2 = MyPerson()
        o2.name = "o2"

        assertEquals("o1", o1.name)
        assertEquals("o2", o2.name)
        assertEquals("o1", o1.getStringProperty("name"))
        assertEquals("o2", o2.getStringProperty("name"))
    }

    @Test
    fun testIntProperties() {
        val p1 = MyPerson()
        assertEquals(0, p1.age)

        p1.age = 42
        assertEquals(42, p1.age)
        assertEquals(42, p1.getIntProperty("age"))

        p1.setProperty("age", 31)
        assertEquals(31, p1.getIntProperty("age"))
        assertEquals(31, p1.age)

        val p2 = MyPerson()
        assertEquals(0, p2.age)

        // one way binding
        p1.bindProperty("age", p2, "age")
        p1.age = 1
        assertEquals(1, p1.age)
        assertEquals(1, p2.age)

        p2.age = 3
        assertEquals(1, p1.age)
        assertEquals(3, p2.age)

        // two way binding

        val p3 = MyPerson()
        val p4 = MyPerson()
        p3.bindProperty("age", p4, "age", G_BINDING_BIDIRECTIONAL)
        p3.age = 19
        assertEquals(19, p3.age)
        assertEquals(19, p4.age)

        p4.age = 21
        assertEquals(21, p3.age)
        assertEquals(21, p4.age)
    }

}

private class MyPerson : Object {
    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(newInstancePointer())

    var name: String by NAME_PROPERTY
    var surname: String? by SURNAME_PROPERTY
    var age: Int by AGE_PROPERTY

    companion object : ObjectCompanion<MyPerson>() {
        override val typeName = "MyPerson"
        override val parentType = Object.typeInfo

        private val NAME_PROPERTY = stringProperty(
            MyPerson::name,
            "name",
            defaultValue = ""
        )
        private val SURNAME_PROPERTY =
            nullableStringProperty(
                MyPerson::surname,
                "surname",
                defaultValue = null
            )
        private val AGE_PROPERTY = intProperty(
            MyPerson::age,
            "age",
            "age",
            "A persons age",
            0,
            200,
            0
        )

        override fun classInit(klass: ObjectClass<MyPerson>) {
            klass.installProperty(NAME_PROPERTY)
            klass.installProperty(SURNAME_PROPERTY)
            klass.installProperty(AGE_PROPERTY)
        }
    }
}
