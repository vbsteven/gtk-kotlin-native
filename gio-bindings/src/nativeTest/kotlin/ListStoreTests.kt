import bindings.gio.ListStore
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.asObject
import native.gobject.GObject
import native.gobject.G_TYPE_OBJECT
import kotlin.test.Test
import kotlin.test.assertEquals

class ListStoreTests {

    @Test
    fun createListStore() {
        val listStore = ListStore(G_TYPE_OBJECT)
        assertEquals(G_TYPE_OBJECT, listStore.itemType)

        val o1 = Object()
        val o2 = Object()
        val o3 = Object()

        listStore.appendAll(o1, o2, o3)
        assertEquals(3, listStore.itemCount)
    }

    @Test
    fun testRemove() {
        val listStore = ListStore(G_TYPE_OBJECT)

        val o1 = Object()
        val o2 = Object()
        val o3 = Object()

        listStore.appendAll(o1, o2, o3)
        listStore.remove(1)
        assertEquals(2, listStore.itemCount)

        val first = listStore.getObject(0)!!.asTypedPointer<GObject>().asObject()
        val last = listStore.getObject(1)!!.asTypedPointer<GObject>().asObject()

        assertEquals(o1.gPointer, first.gPointer)
        assertEquals(o3.gPointer, last.gPointer)

        listStore.removeAll()
        assertEquals(0, listStore.itemCount)
    }

    @Test
    fun testInsert() {
        val listStore = ListStore(G_TYPE_OBJECT)

        val o1 = Object()
        val o2 = Object()
        val o3 = Object()
        val o4 = Object()

        listStore.appendAll(o1, o2, o3)
        listStore.insert(2, o4)
        assertEquals(4, listStore.itemCount)

        val fetched = listStore.getObject(2)!!.asTypedPointer<GObject>().asObject()
        assertEquals(o4.gPointer, fetched.gPointer)
    }

}