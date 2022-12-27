package bindings.adw

import kotlinx.cinterop.CPointer
import native.adwaita.*

fun CPointer<AdwApplication>.asApplication(): Application = Application(this)
fun CPointer<AdwApplicationWindow>.asApplicationWindow(): ApplicationWindow = ApplicationWindow(this)
fun CPointer<AdwAvatar>.asAvatar(): Avatar = Avatar(this)
fun CPointer<AdwHeaderBar>.asHeaderBar(): HeaderBar = HeaderBar(this)
fun CPointer<AdwWindow>.asWindow(): Window = Window(this)
