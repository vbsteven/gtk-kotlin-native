package bindings.gio

import bindings.gio.internal.ActionMapWrapper
import bindings.gio.internal.ActionWrapper
import bindings.gio.internal.ListModelWrapper
import kotlinx.cinterop.CPointer
import native.gio.*

fun CPointer<GAction>.asAction(): Action = ActionWrapper(this)
fun CPointer<GActionMap>.asActionMap(): ActionMap = ActionMapWrapper(this)
fun CPointer<GApplication>.asApplication(): Application = Application(this)
fun CPointer<GListModel>.asListModel(): ListModel = ListModelWrapper(this)
fun CPointer<GListStore>.asListStore(): ListStore = ListStore(this)
fun CPointer<GMenu>.asMenu(): Menu = Menu(this)
fun CPointer<GMenuItem>.asMenuItem(): MenuItem = MenuItem(this)
fun CPointer<GMenuModel>.asMenuModel(): MenuModel = MenuModel(this)
fun CPointer<GPermission>.asPermission(): Permission = Permission(this)
fun CPointer<GSimplePermission>.asSimplePermission(): SimplePermission = SimplePermission(this)


