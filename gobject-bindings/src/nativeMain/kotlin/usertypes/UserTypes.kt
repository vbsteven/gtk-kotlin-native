package usertypes

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.KGTypeInfo
import internal.TypeRegistry
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import native.gobject.*

/**
 * Initializer for user-defined Object subclasses.
 */
typealias ObjectClassInitFunc = (klass: ObjectClass) -> Unit

open class ObjectClass(val pointer: CPointer<*>) {
    val gType: GType = pointer.asTypedPointer<GTypeClass>().pointed.g_type

    /**
     * Install a signal to this object class.
     *
     * @param signalName name of the signal
     * @param signalFlags signal flags to pass on to g_signal_new
     * @throws Error when the signal is invalid
     */
    fun installSignal(
        signalName: String,
        signalFlags: GSignalFlags
    ) {
        // TODO validate signal name correctness
        val signal = g_signal_new(
            signalName,
            gType,
            signalFlags,
            0,
            null,
            null,
            null,
            G_TYPE_NONE,
            0,
        )

        if (signal.toInt() == 0) {
            throw Error("Error installing signal $signalName")
        }
    }
}


/**
 * Register a new type class.
 */
fun <T : Object> registerTypeClass(
    typeName: String,
    superType: KGTypeInfo<*>,
    classInitFunc: ObjectClassInitFunc = {}
): KGTypeInfo<T> {
    return TypeRegistry.registerType(typeName, superType, classInitFunc)
}