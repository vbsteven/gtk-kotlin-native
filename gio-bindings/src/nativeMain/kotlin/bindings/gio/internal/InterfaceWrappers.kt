package bindings.gio.internal

import bindings.gio.Action
import bindings.gio.ActionMap
import bindings.gio.ListModel
import bindings.gobject.asTypedPointer
import kotlinx.cinterop.CPointer
import native.gio.GAction
import native.gio.GActionMap
import native.gio.GListModel

internal class ActionWrapper(
    override val gActionPointer: CPointer<GAction>
) : Action

internal class ActionMapWrapper(
    override val gActionMapPointer: CPointer<GActionMap>
) : ActionMap

internal class ListModelWrapper(pointer: CPointer<*>) : ListModel {
    override val gListModelPointer = pointer.asTypedPointer<GListModel>()
}