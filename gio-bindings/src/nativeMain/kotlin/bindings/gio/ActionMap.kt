package bindings.gio

import kotlinx.cinterop.CPointer
import native.gio.GActionMap
import native.gio.g_action_map_add_action
import native.gio.g_action_map_lookup_action
import native.gio.g_action_map_remove_action

interface ActionMap {

    val gActionMapPointer: CPointer<GActionMap>

    fun addAction(action: Action) = g_action_map_add_action(gActionMapPointer, action.gActionPointer)

    // TODO addActionEntries with varargs or a list

    fun lookupAction(actionName: String) = g_action_map_lookup_action(gActionMapPointer, actionName)?.asAction()

    fun removeAction(actionName: String) = g_action_map_remove_action(gActionMapPointer, actionName)
}

fun CPointer<GActionMap>.asActionMap(): ActionMap = ActionMapWrapper(this)

private class ActionMapWrapper(
    override val gActionMapPointer: CPointer<GActionMap>
) : ActionMap