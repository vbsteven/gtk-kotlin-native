package bindings.adw

import kotlinx.cinterop.CPointer
import native.adwaita.AdwApplication
import native.adwaita.AdwApplicationWindow
import native.adwaita.AdwWindow

fun CPointer<AdwApplication>.asApplication(): Application = Application(this)
fun CPointer<AdwApplicationWindow>.asApplicationWindow(): ApplicationWindow = ApplicationWindow(this)
fun CPointer<AdwWindow>.asWindow(): Window = Window(this)
