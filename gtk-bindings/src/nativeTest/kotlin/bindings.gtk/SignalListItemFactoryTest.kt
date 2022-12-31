package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class SignalListItemFactoryTest : GtkTestBase() {

    @Test
    fun testSignalListItemFactory() = runTestApplicationWindow { window ->
        val items = List(200) { "Item $it" }
        val model = StringList(items)
        val factory = SignalListItemFactory()
        val selectionModel = SingleSelection(model)

        factory.onSetup { listItem ->
            println("setup")
            listItem.child = Label()
        }
        factory.onBind { listItem ->
            val label = listItem.child?.let { Label.fromObject(it) }!!
            val item = listItem.item?.let { StringObject.fromObject(it) }!!
            label.text = item.string
        }
        factory.onUnbind {
            println("unbind")
        }
        factory.onTeardown {
            println("teardown")
        }

        val listView = ListView(selectionModel, factory)

        window.child = ScrolledWindow(listView)
    }
}