package bindings.gio

import bindings.glib.Variant

fun ActionMap.addAction(actionName: String, handler: () -> Unit) = addAction(SimpleAction(actionName).apply {
    onActivate { handler() }
})

fun ActionMap.addAction(actionName: String, handler: (Variant?) -> Unit) = addAction(SimpleAction(actionName).apply {
    onActivate(handler)
})