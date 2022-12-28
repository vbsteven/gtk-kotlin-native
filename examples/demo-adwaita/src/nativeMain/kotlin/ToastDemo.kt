import bindings.adw.*
import bindings.gtk.Button
import bindings.gtk.Widget
import native.gtk.GtkAlign

lateinit var overlay: ToastOverlay

fun buildToastDemoPage(): Widget {
    overlay = ToastOverlay()
    overlay.vexpand = true

    val page = StatusPage().apply {
        vexpand = true
        title = "Toasts"
        child = buildToastList()
        valign = GtkAlign.GTK_ALIGN_START
    }

    overlay.child = page

    return overlay
}

fun buildToastList(): Widget {

    val prefGroup = PreferencesGroup()

    prefGroup.add(demoToastRow("Simple Toast", ::showSimpleToast))
    prefGroup.add(demoToastRow("Toast with button", ::showButtonToast))
    prefGroup.add(demoToastRow("Custom Title", ::showCustomTitleToast))

    return Clamp().apply {
        maximumSize = 400
        tighteningThreshold = 300
        child = prefGroup
    }
}

private fun demoToastRow(label: String, handler: () -> Unit) = ActionRow().apply {
    this.title = label
    this.addSuffix(
        Button("Show").apply {
            valign = GtkAlign.GTK_ALIGN_CENTER
            onClicked { handler() }
        }
    )
}

fun showSimpleToast() = overlay.addToast(Toast("A simple toast"))
fun showButtonToast() = overlay.addToast(Toast("A button toast").apply {
    buttonLabel = "Click me"
})

fun showCustomTitleToast() = overlay.addToast(Toast("Custom Title").apply {
    customTitle = Avatar(30, null, false)
})
