import bindings.adw.Application
import bindings.adw.ApplicationWindow
import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import bindings.gtk.Button
import kotlinx.cinterop.*
import native.gobject.*
import kotlin.test.Test
import kotlin.test.assertTrue

class ApplicationUnrefTest {

    @Test
    fun testDisposeTracker() {
        val app = Application("io.quantus.test")
        val applicationTracker = DisposeTracker(app)
        val windowTracker = DisposeTracker()
        val widgetTracker = DisposeTracker()

        app.onActivate {
            val window = ApplicationWindow(app)
            windowTracker.track(window)

            val button = Button("My Button")
            widgetTracker.track(button)
            window.content = button
            window.show()
        }
        app.runApplication()
        app.unref()

        assertTrue(applicationTracker.isDisposed, "Application was not disposed")
        assertTrue(windowTracker.isDisposed, "Window was not disposed")
        assertTrue(widgetTracker.isDisposed, "Button was not disposed")
    }

    @Test
    fun testMultiDisposeTracker() {
        val app = Application("io.quantus.test")

        val tracker = MultiDisposeTracker()
        tracker.track(app)

        app.onActivate {
            val window = ApplicationWindow(app)
            tracker.track(window)

            val button = Button("My Button")
            tracker.track(button)

            window.content = button
            window.show()
        }
        app.runApplication()
        app.unref()

        tracker.assertAllDisposed()
    }
}

/**
 * Helper class that tracks [obj] by adding a weak reference to it
 * and setting [isDisposed] to true when the weak reference was nulled.
 */
private class DisposeTracker {

    var isDisposed = false
    var isTracking = false
    var name: String = ""

    constructor(name: String? = null) {
        this.name = name ?: "unknown"
    }

    constructor(obj: Object, name: String? = null) : this(name) {
        track(obj)
    }

    fun track(obj: Object) {
        if (isTracking) throw Error("DisposeTracker track() was called more than once")
        isTracking = true

        name = obj::class.simpleName ?: "unknown"

        g_object_weak_ref(
            obj.gPointer.asTypedPointer<GObject>(),
            weakNotify,
            StableRef.create(this).asCPointer()
        )
    }

    fun disposeCalled() {
        println("## DisposeTracker object disposed :: $name")
        isDisposed = true
    }
}

/**
 * Helper class for tracking multiple objects and asserting
 * whether they are all disposed at the end of a test.
 */
private class MultiDisposeTracker() {
    private val trackers = mutableListOf<DisposeTracker>()

    /**
     * The number of objects tracked
     */
    val count get() = trackers.size

    /**
     * true when all trackers have been disposed
     */
    val isAllDisposed: Boolean
        get() = trackers.all { it.isDisposed }

    /**
     * A list of names for all objects that have not been disposed
     */
    val undisposedObjects: List<String>
        get() = trackers.filter { !it.isDisposed }
            .map { it.name }

    fun track(obj: Object, name: String? = null) {
        trackers.add(DisposeTracker(obj, name))
    }

    fun assertAllDisposed() {
        println("MultiDisposeTracker asserting isDisposed for $count objects")

        if (isAllDisposed) {
            println("All tracked objects have been disposed")
        } else {
            undisposedObjects.let {
                println("${it.count()} out of $count objects were not disposed:")
                it.forEach {
                    println("Undisposed object :: $it")
                }

                throw Error("${it.count()} objects were not disposed: (${it.joinToString(", ")})")
            }
        }
    }
}

private val weakNotify: GWeakNotify = staticCFunction { data: gpointer, _: CPointer<GObject> ->
    val stableRef = data.asStableRef<DisposeTracker>()
    stableRef.get().disposeCalled()
    stableRef.dispose()
    Unit
}.reinterpret()