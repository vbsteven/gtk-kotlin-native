package bindings.gio

import bindings.gobject.boolean
import bindings.util.toStringList
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import native.gio.GActionGroup
import native.gio.g_action_group_get_action_enabled
import native.gio.g_action_group_has_action
import native.gio.g_action_group_list_actions
import native.gobject.g_strfreev

interface ActionGroup {
    val gActionGroupPointer: CPointer<GActionGroup>

    fun hasAction(actionName: String): Boolean = g_action_group_has_action(gActionGroupPointer, actionName).boolean

    fun getActionEnabled(actionName: String): Boolean =
        g_action_group_get_action_enabled(gActionGroupPointer, actionName).boolean

    val actions: List<String>
        get() = memScoped {
            val a = g_action_group_list_actions(gActionGroupPointer)!!
            val actions = a.toStringList()
            g_strfreev(a)
            actions
        }
}