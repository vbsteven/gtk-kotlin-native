package bindings.adw

import kotlinx.cinterop.CPointer
import native.adwaita.*

fun CPointer<AdwActionRow>.asActionRow(): ActionRow = ActionRow(this)
fun CPointer<AdwApplication>.asApplication(): Application = Application(this)
fun CPointer<AdwAboutWindow>.asAboutWindow(): AboutWindow = AboutWindow(this)
fun CPointer<AdwApplicationWindow>.asApplicationWindow(): ApplicationWindow = ApplicationWindow(this)
fun CPointer<AdwAvatar>.asAvatar(): Avatar = Avatar(this)
fun CPointer<AdwButtonContent>.asButtonContent(): ButtonContent = ButtonContent(this)
fun CPointer<AdwClamp>.asClamp(): Clamp = Clamp(this)
fun CPointer<AdwHeaderBar>.asHeaderBar(): HeaderBar = HeaderBar(this)
fun CPointer<AdwMessageDialog>.asMessageDialog(): MessageDialog = MessageDialog(this)
fun CPointer<AdwPreferencesRow>.asPreferencesRow(): PreferencesRow = PreferencesRow(this)
fun CPointer<AdwStatusPage>.asStatusPage(): StatusPage = StatusPage(this)
fun CPointer<AdwWindow>.asWindow(): Window = Window(this)
fun CPointer<AdwTabBar>.asTabBar(): TabBar = TabBar(this)
fun CPointer<AdwTabView>.asTabView(): TabView = TabView(this)
fun CPointer<AdwTabPage>.asTabPage(): TabPage = TabPage(this)
fun CPointer<AdwToast>.asToast(): Toast = Toast(this)
fun CPointer<AdwToastOverlay>.asToastOverlay(): ToastOverlay = ToastOverlay(this)
