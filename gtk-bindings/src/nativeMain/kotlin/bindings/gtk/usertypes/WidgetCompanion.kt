package bindings.gtk.usertypes

import bindings.gtk.Button
import bindings.gtk.Widget
import internal.KGType
import internal.objectproperties.ObjectClassProperties
import kotlinx.cinterop.*
import native.gobject.GVariant
import native.gobject.g_object_new
import native.gobject.g_type_name_from_instance
import native.gtk.GtkWidget
import native.gtk.GtkWidgetActionActivateFunc
import native.gtk.gtk_widget_class_install_action
import usertypes.ObjectClass
import usertypes.registerTypeClass

private val WIDGET_COMPANION_MAP = mutableMapOf<String, WidgetCompanion<*>>()

/**
 * Companion base class used for registering custom [Widget] types in the GObject type system.
 *
 * @param T class for which this companion will register a new type.
 */
abstract class WidgetCompanion<T : Widget> {
    /**
     * Name of the widget type.
     */
    abstract val typeName: String

    /**
     * Parent type info.
     *
     * This should be the typeInfo object of the class that your own class extends.
     *
     * For example if your class subclasses [Button] this value should
     * be set to [Button.Type].
     */
    abstract val parentType: KGType<*>

    /**
     * Class initializer.
     *
     * This method will be invoked once during the type registration process.
     *
     * @param klass The WidgetClass instance that can be used for installing
     *              actions, properties and signals on your custom type.
     * @see WidgetClass
     */
    open fun classInit(klass: WidgetClass<T>) {}

    /**
     * Instantiate a new instance pointer of the associated Widget subclass.
     *
     * This method should be used in constructors of the associated Widget subclass
     * and the resulting pointer should be passed on to the constructor of the parent class.
     *
     * @return a pointer to the instance
     */
    fun newInstancePointer(): CPointer<*> = g_object_new(Type.gType, null)!!

    /**
     * Type property of the associated Widget class.
     *
     * Accessing this property lazily triggers the type registration process.
     */
    val Type by lazy {
        // lazy register because we want gtk_init to be called first
        registerType()
    }

    /* Action handling */

    private val actionHandlerMap = mutableMapOf<String, (T) -> Unit>()

    internal fun invokeActionOnInstance(instancePointer: CPointer<GtkWidget>, actionName: String) {
        // dispatch
        actionHandlerMap[actionName]?.invoke(
            Type.instanceFromPointer(instancePointer)
        )
    }

    /* Type registration */

    /**
     * Actual type registration, this should only be called once.
     */
    private fun registerType(): KGType<T> {
        val info = registerTypeClass<T>(
            typeName,
            parentType
        ) { objectClass: ObjectClass<*> ->
            // TODO the way objectProperties are passed should be handled better.
            val widgetClass = WidgetClass<T>(objectClass.pointer, actionHandlerMap, objectClass.objectProperties)
            // delegate to user init
            classInit(widgetClass)
        }

        // also register in companion map for later lookups
        WIDGET_COMPANION_MAP[typeName] = this

        return info
    }
}


class WidgetClass<T : Widget> internal constructor(
    pointer: CPointer<*>,
    private val actionHandlerMap: MutableMap<String, (T) -> Unit>,
    objectClassProperties: ObjectClassProperties
) : ObjectClass<T>(pointer, objectClassProperties) {

    /**
     * Install action in this widget class.
     *
     * When the action is activated for an instance of this class, [actionFunc] is invoked.
     * @param actionName name of the action
     * @param actionFunc handler to invoke when action is activated
     */
    fun installAction(actionName: String, actionFunc: (T) -> Unit) {
        // save for later dispatching
        actionHandlerMap[actionName] = actionFunc

        gtk_widget_class_install_action(pointer.reinterpret(), actionName, null, staticWidgetActionActivateFunc)
    }

}

private val staticWidgetActionActivateFunc: GtkWidgetActionActivateFunc =
    staticCFunction { widgetPointer: CPointer<GtkWidget>,
                      actionNamePointer: CPointer<ByteVar>,
                      parameter: CPointer<GVariant>? ->
        // TODO add parameter support, this implementation only supports no-arg actions
        val actionName = actionNamePointer!!.toKString()
        val typeName = g_type_name_from_instance(widgetPointer.reinterpret())
            ?.toKString()
            ?.let { WIDGET_COMPANION_MAP[it] }
            ?.invokeActionOnInstance(widgetPointer, actionName)
        Unit
    }.reinterpret()