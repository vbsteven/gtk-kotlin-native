package usertypes

import bindings.gobject.Object
import bindings.gobject.asTypedPointer
import internal.KGTypeInfo
import internal.TypeRegistry
import internal.objectproperties.IntObjectProperty
import internal.objectproperties.NullableStringObjectProperty
import internal.objectproperties.ObjectClassProperties
import internal.objectproperties.StringObjectProperty
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import native.gobject.*
import kotlin.reflect.KMutableProperty1

/**
 * Initializer for user-defined Object subclasses.
 */
typealias ObjectClassInitFunc = (klass: ObjectClass<*>) -> Unit

open class ObjectClass<T : Object> constructor(
    val pointer: CPointer<*>,
    val objectProperties: ObjectClassProperties
) {
    val gType: GType = pointer.asTypedPointer<GTypeClass>().pointed.g_type

    private val objectClassPointer get() = pointer.asTypedPointer<GObjectClass>()

    /* Signals */

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

    /* Properties */

    fun installProperty(property: KGObjectProperty<*, *>) = objectProperties.installProperty(property)
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


fun <OBJECT_TYPE : Object> stringProperty(
    property: KMutableProperty1<OBJECT_TYPE, String>,
    name: String,
    nick: String? = null,
    blurb: String? = null,
    defaultValue: String,
    flags: GParamFlags = G_PARAM_READWRITE
): KGObjectProperty<OBJECT_TYPE, String> = StringObjectProperty(property, name, nick, blurb, defaultValue, flags)

fun <OBJECT_TYPE : Object> nullableStringProperty(
    property: KMutableProperty1<OBJECT_TYPE, String?>,
    name: String,
    nick: String? = null,
    blurb: String? = null,
    defaultValue: String?,
    flags: GParamFlags = G_PARAM_READWRITE
): KGObjectProperty<OBJECT_TYPE, String?> =
    NullableStringObjectProperty(property, name, nick, blurb, defaultValue, flags)

fun <OBJECT_TYPE : Object> intProperty(
    property: KMutableProperty1<OBJECT_TYPE, Int>,
    name: String,
    nick: String? = null,
    blurb: String? = null,
    minimum: Int,
    maximum: Int,
    defaultValue: Int,
    flags: GParamFlags = G_PARAM_READWRITE
): KGObjectProperty<OBJECT_TYPE, Int> =
    IntObjectProperty(property, name, nick, blurb, minimum, maximum, defaultValue, flags)
