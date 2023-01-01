package listmodel

import bindings.gtk.*

/**
 * An example showing how to build a GTK4 [ListView] with string items.
 */
fun main() {
    val app = Application("io.quantus.gtk-kotlin-native.example.listviewmodel")
    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "ListModel"
        window.defaultSize = Pair(600, 400)
        buildUI(window)
        window.show()
    }
    app.run()
    app.unref()
}

private fun buildUI(window: Window) {

    // create a list of String items
    val items = List(200) { "Item $it" }

    // wrap in a StingList (ListModel)
    val model = StringList(items)

    // setup the item factory
    val factory = SignalListItemFactory()

    // prepare the item row
    factory.onSetup { listItem ->
        listItem.child = Label()
    }

    // bind the item row
    factory.onBind { listItem ->
        // get the label from the listItem
        val label = Label.fromObject(listItem.child!!)

        // get the StringObject from the listItem
        val item = StringObject.fromObject(listItem.item!!)

        // update the label
        label.text = item.string
    }

    val selectionModel = SingleSelection(model)
    val listView = ListView(selectionModel, factory)

    window.child = ScrolledWindow(listView)
}