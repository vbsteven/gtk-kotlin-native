package bindings.gtk

import bindings.gio.ListModel
import bindings.gio.asListModel
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.internal.staticStableRefDestroy
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gio.GListModel
import native.gobject.gpointer
import native.gtk.*

class TreeListModel : Widget, ListModel {
    val gtkTreeListModelPointer get() = gtkWidgetPointer.asTypedPointer<GtkTreeListModel>()
    override val gListModelPointer get() = gtkWidgetPointer.asTypedPointer<GListModel>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor(
        root: ListModel,
        passthrough: Boolean,
        autoexpand: Boolean,
        createModelFunc: TreeListModelCreateModelFunc
    ) : this(initTreeListModel(root, passthrough, autoexpand, createModelFunc))

    var autoexpand: Boolean
        get() = gtk_tree_list_model_get_autoexpand(gtkTreeListModelPointer).boolean
        set(value) = gtk_tree_list_model_set_autoexpand(gtkTreeListModelPointer, value.gboolean)

    fun getChildRow(position: Int): TreeListRow? =
        gtk_tree_list_model_get_child_row(gtkTreeListModelPointer, position.toUInt())?.asTreeListRow()

    val model: ListModel = gtk_tree_list_model_get_model(gtkTreeListModelPointer)!!.asListModel()

    val passthrough: Boolean get() = gtk_tree_list_model_get_passthrough(gtkTreeListModelPointer).boolean


    companion object {
        private fun initTreeListModel(
            root: ListModel,
            passthrough: Boolean,
            autoexpand: Boolean,
            createModelFunc: TreeListModelCreateModelFunc
        ): CPointer<GtkTreeListModel> {
            return gtk_tree_list_model_new(
                root.gListModelPointer,
                passthrough.gboolean,
                autoexpand.gboolean,
                staticTreeListModelCreateModelFunc,
                StableRef.create(createModelFunc).asCPointer(),
                staticStableRefDestroy.reinterpret()
            )!!
        }

        val Type = BuiltinTypeInfo(GTK_TYPE_TREE_LIST_MODEL, ::TreeListModel)
    }

}

typealias TreeListModelCreateModelFunc = (item: Object) -> ListModel?

private val staticTreeListModelCreateModelFunc: GtkTreeListModelCreateModelFunc =
    staticCFunction { item: gpointer,
                      data: gpointer? ->
        data?.asStableRef<TreeListModelCreateModelFunc>()?.get()?.invoke(Object(item))?.gListModelPointer
    }.reinterpret()
