package treelistmodel

import bindings.gio.ListStore
import bindings.gobject.Object
import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gtk.*
import kotlinx.cinterop.CPointer
import native.gtk.GtkLabel
import native.gtk.GtkOrientation
import native.gtk.GtkTreeExpander

/**
 * An example showing how to use a GTK4 [TreeListModel] to display an expandable tree of items.
 */
fun main() {
    val app = Application("io.quantus.gtk-kotlin-native.example.treelistmodel")
    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "TreeListModel Example"
        window.defaultSize = Pair(600, 400)
        buildWindow(window)
        window.show()
    }
    app.run()
    app.unref()
}


fun buildWindow(window: Window) {
    // create a listStore to store the items
    val listStore = ListStore(Task.typeInfo.gType)

    // build a tree of Tasks with children
    listStore.appendAll(
        Task(
            "Root 1", listOf(
                Task("Child 1", emptyList()),
                Task(
                    "Child 2",
                    listOf(
                        Task("Sub Child 1", emptyList()),
                        Task("Sub Child 2", emptyList())
                    )
                )
            )
        ),
        Task("Root 2", emptyList())
    )

    // setup the item factory
    val factory = SignalListItemFactory()

    // prepare the item row
    factory.onSetup { listItem ->
        listItem.child = Box(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 10).apply {
            append(TreeExpander().apply { indentForIcon = true })
            append(Label())
        }
    }

    // bind a Task item to a row
    factory.onBind { listItem ->
        // get the box widget from the listItem
        val box = listItem.child?.let { Box(it.gPointer) }!!

        // get the expander and label widgets from the box
        val expander = box.firstChild!!.gPointer.asTypedPointer<GtkTreeExpander>().asTreeExpander()
        val label = box.lastChild!!.gPointer.asTypedPointer<GtkLabel>().asLabel()

        // get the row from the listItem
        val row = TreeListRow(listItem.item!!.gPointer)
        // associate the expander with the row
        expander.listRow = row

        // get the task object from the row
        val task = Task.fromObject(row.item!!)

        // set the label text
        label.text = task.name
    }

    // wrap the listStore in a TreeListModel
    // the trailing lambda is called for an item and needs to return a ListModel for its children
    val treeListModel = TreeListModel(listStore, passthrough = false, autoexpand = true) {
        // convert the object to our Task instance
        val task = Task.fromObject(it)
        if (task.children.isNotEmpty()) {
            // return a listStore of children
            ListStore(task.gType, task.children)
        } else {
            null
        }
    }

    // wrap the tree model in a selection
    val selectionModel = SingleSelection(treeListModel)

    // create the ListView, passing in the selection model and the factory
    val listView = ListView(selectionModel, factory)

    // add the list to the window
    window.child = ScrolledWindow(listView)
}

/**
 * A Task Object class that has a name and children.
 */
private class Task : Object {
    lateinit var name: String
    lateinit var children: List<Task>

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(name: String, children: List<Task>) : this(newInstancePointer()) {
        this.name = name
        this.children = children
    }

    companion object : ObjectCompanion<Task>() {
        override val typeName = "Task"
        override val parentType = Object.typeInfo
    }
}
