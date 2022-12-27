package bindings.gtk

import bindings.gobject.Object
import bindings.gtk.testutils.GtkTestBase
import usertypes.registerTypeClass
import native.gobject.g_object_new
import native.gobject.g_object_ref_sink
import native.gobject.g_object_unref
import kotlin.test.Test

class CustomWidgetTest : GtkTestBase() {

    @Test
    fun tmpInstantiateCustomWidget() {
        val widget = g_object_new(MyCustomWidgetTypeInfo.gType, null)
        println("widget: $widget")
    }

    @Test
    fun testCustomWidgetDispose() {
        val widget = g_object_new(MyCustomWidgetTypeInfo.gType, null)
        g_object_unref(widget)

        val w2 = AnotherWidget()
        g_object_ref_sink(w2.gPointer)
        g_object_unref(w2.gPointer)
    }

}

class MyCustomWidget : Button {
    constructor() : super()
}

val MyCustomWidgetTypeInfo = registerTypeClass<MyCustomWidget>(
    "MyCustomWidget", Button.typeInfo,
)

class AnotherWidget : Object {
    constructor() : super(AnotherWidgetTypeInfo.newInstancePointer())
}

val AnotherWidgetTypeInfo = registerTypeClass<AnotherWidget>("AnotherWidget", Button.typeInfo)