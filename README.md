# GTK Kotlin/Native bindings

Write GTK applications using Kotlin/Native.

This project provides binding modules for GTK4 and related GNOME libraries combined with
utility modules that make it possible to write GTK desktop applications in idiomatic Kotlin.

This project **does not** provide bindings for the full GTK API surface and all of its GNOME dependencies.

The main goal is to provide enough glue for application developers familiar with Kotlin to
get started writing GTK applications without having to dig into the details of the C API.

While this project aims to wrap/abstract the existing C API into an idiomatic(-ish) Kotlin API, it does not
hide the underlying C API from developers. When a Kotlin version of a type, method or function
is not available, the original C version can still be used.


## Motivation

This is an experimental holiday hacking project, mostly for learning purposes.
I started this project because I wanted to experiment with writing GTK applications using Kotlin/Native.

While the [gtk-kt project](https://gitlab.com/gtk-kt/gtk-kt) already provides bindings for various GNOME libraries
including Gtk4 and libadwaita, I decided to start from scratch as a learning experience and deep-dive into both Kotlin/Native and GTK.

My endgame is a task/calendar/note application that I want to make available on desktop and mobile platforms while using
as much shared code as possible. The idea is to write the shared code as a Kotlin multiplatform project and on top of
that use GTK for desktop platforms, UIKit with Kotlin/Native for iOS and Kotlin for Android.


## High Level Overview

This repository contains one multi-module Gradle project containing modules for library bindings and modules for 
extensions and utility libraries.

### Gradle modules

| Module              | Description                                                                                                      |
|---------------------|------------------------------------------------------------------------------------------------------------------|
| gobject-bindings    | Kotlin bindings for [GObject](https://docs.gtk.org/gobject/).                                                    |
| gio-bindings        | Kotlin bindings for [GIO](https://docs.gtk.org/gio/).                                                            |
| gtk-bindings        | Kotlin bindings for [GTK4](https://docs.gtk.org/gtk4/).                                                          |
| libadwaita-bindings | Kotlin bindings for [libadwaita](https://gnome.pages.gitlab.gnome.org/libadwaita/doc/1.2/) (target version 1.2). |


## Examples

### Hello World

A hello world example using only GTK.

```kotlin
import bindings.gtk.Application
import bindings.gtk.ApplicationWindow
import bindings.gtk.Label

fun main() {
    val app = Application("com.example.app")

    app.onActivate {
        val window = ApplicationWindow(app)
        window.title = "Hello World"
        window.defaultSize = Pair(600, 400)
        window.child = Label("Hello from Kotlin/Native")
        window.show()
    }

    app.runApplication()
    app.unref()
}
```

### Counter

A counter example using only GTK.

```kotlin
fun main() {
    val app = Application("com.example.counter")

    var counter = 0

    app.onActivate {
        // create the window
        val window = ApplicationWindow(app)
        window.title = "Hello World"
        window.defaultSize = Pair(600, 400)

        // create the widgets
        val label = Label()
        val button = Button("Click me")

        // attach a button click handler
        button.onClicked {
            label.text = "You clicked ${++counter} times"
        }

        // wrap in a box for layout
        window.child = Box(GTK_ORIENTATION_VERTICAL, 20).apply {
            valign = GTK_ALIGN_CENTER
            append(button)
            append(label)
        }

        // show the window
        window.show()
    }

    app.runApplication()
    app.unref()
}
```

Other examples:
* [TreeListModel](examples/gtk-examples/src/nativeMain/kotlin/treelistmodel/TreeListModelExample.kt)
* [ListModel](examples/gtk-examples/src/nativeMain/kotlin/listmodel/ListModelExample.kt)
* [GridView](examples/gtk-examples/src/nativeMain/kotlin/gridview/GridViewExample.kt)
* [Custom Widget subclass and property binding](examples/gtk-examples/src/nativeMain/kotlin/customwidgetbinding/CustomWidgetBindingExample.kt)


See the [examples](examples) folder for more examples.

## Dependencies

Currently building this project requires fairly recent versions of its dependencies. I'll try to get the required
dependency versions down a little in the near future.

* gio >= 2.74
* gtk4 >= 4.6.6
* libadwaita-1 >= 1.2 (optional, only when using libadwaita-bindings)


## Building on Linux

Ubuntu dependencies

```shell
apt install libgtk4-dev gcc-multilib
```

Arch dependencies

```shell
pacman -S gtk4 libadwaita
```

Building the full project (requires libadwaita >= 1.2)

```shell
./gradlew build -x test
```

Building only the gtk bindings

```shell
./gradlew gtk-bindings:build -x test
```

## Building on Windows

The project can be built on Windows in the Mingw64 environment.

* Download and install [Mingw64](https://www.mingw-w64.org/)
* Install dependencies in the mingw64 shell 
```shell
pacman -S base-devel mingw-w64-x86_64-toolchain mingw-x86_64-gtk4 mingw-w64-x86_64-libadwaita
```
* Trigger the build
```shell
./gradlew build -x test
```


### Using the binding libraries

This project is not yet published to a repository. The best way to get started is to publish
to your local Maven repository.

```shell
./gradlew publishToMavenLocal -x test
```

And then in your own project build.gradle.kts file:
```
repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
    implementation("io.quantus.gtk-kotlin-native:gtk-bindings:0.0.1-SNAPSHOT")
    implementation("io.quantus.gtk-kotlin-native:libadwaita-bindings:0.0.1-SNAPSHOT")
}
```


## Current State

This project is still highly experimental. There should be enough ported/wrapped to write simple applications.

Basic application, window and widget creation is available. As well as simple property binding, signal handling
and working with actions.

With these basics available, I'm focussing first on getting most of libadwaita wrapped since that provides most of
the building blocks for modern UI applications (preferences, action rows, headers, dialogs, toasts, style classes,...).

After that I'm working on supporting more of GTK (more widgets, shortcuts, search, text entry, file/color/font chooser dialogs,...)
and some parts of GIO (lists, actions, icons, files).

* `gobject-bindings` module
  * basic `GObject` support
  * basic `GVariant` support
  * basic property binding support
  * **experimental** support for subclassing `GObject` in user-defined types.
* `gio-bindings` module
  * Application
  * Actions
    * Action
    * SimpleAction
    * ActionMap
  * Lists
    * ListModel
    * ListStore
  * Menus
    * Menu
    * MenuItem
    * MenuModel
* `gtk-bindings` module
  * Application and Windows
    * Application
    * Window
    * ApplicationWindow
    * PreferencesWindow
    * ShortcutsWindow
  * Widgets
    * Widget
    * Label
    * Button
    * Box
    * ListBox
    * ListBoxRow
    * MenuButton
    * Stack
    * StackPage
    * StackSidebar
    * ToggleButton
    * AspectFrame
    * CenterBox
    * CheckButton
    * Grid
    * GridView
    * HeaderBar
    * Image
    * LevelBar
    * LinkButton
    * ListView
    * LockButton
    * Popover
    * ProgressBar
    * ScrolledWindow
    * SearchBar
    * Separator
    * Switch
  * Dialogs
    * AboutDialog
    * MessageDialog
  * Lists and Trees
    * StringList
    * ListItem
    * ListItemFactory
    * SignalListItemFactory
    * TreeExpander
    * TreeListModel
    * TreeListRow
  * Interfaces
    * Actionable
    * Editable
    * Native
    * Orientable
    * Root
    * SelectionModel
* `libadwaita-bindings` module
  * Application and Windows
    * Application
    * Window
    * ApplicationWindow
    * AboutWindow
  * Widgets
    * ActionRow
    * Avatar
    * ButtonContent
    * ComboRow
    * Clamp
    * EntryRow
    * ExpanderRow
    * Flap
    * HeaderBar
    * Leaflet
    * LeafletPage
    * PasswordEntryRow
    * StatusPage
    * TabView
    * TabBar
    * TabPage
  * Message Dialogs
  * Preferences
    * PreferencesGroup
    * PreferencesRow
    * PreferencesPage
    * PreferencesWindow
  * Toasts
    * ToastOverlay
    * Toast
  * ViewStack
    * ViewStack
    * ViewStackPage
    * ViewSwitcher
    * ViewSwitcherBar
    * ViewSwitcherTitle


## Future

Some things I would like to experiment with in the near future:

### Coroutines

A `coroutines-gtk` module that adds coroutine support providing `Dispatchers.Gtk` context and `Dispatcher.Main` 
implementation.  

A set of extension properties providing `CoroutineScopes` for `Application`, `Window` and `Widget`.
I'm thinking something similar to how Android provides `viewModelScope` and `lifecycleScope` properties.

```kotlin
windowScope.launch {
    // Coroutine that is cancelled when the window closes.
}

```

### Typed interfaces

The current interface bindings (like `ListModel` for `GListModel`) don't have any type information. This means getting an 
item from a `ListModel` will always return an `Object` instance which the user needs to downcast to the expected type.

I want to experiment with adding type parameters to the bindings so that when a method returns a model for which the return
type is known, it can return a `ListModel<SomeType>`.

### Better support for subclassing GObject and GtkWidget

~~Find a better way to create GObject subclasses and create custom widgets.~~

See [Deriving custom widget classes](#deriving-custom-widget-classes) 
and [Deriving custom object classes with properties](#deriving-custom-object-classes-with-properties)

### Building on Windows and macOS

Currently it builds on Linux and Window. macOS support is on my list.

### Generate bindings using GIR

While [GObject Introspection - GIR](https://gi.readthedocs.io/en/latest/) provides a way to extract type information from
GObject-based libaries, I decided to not use it for now and write the first version manually. Writing the bindings manually
forces me to look at the GObject API in detail (which I had not worked with before), and allows me to iterate fast on various
approaches.

When the library matures and I have a few applications under my belt, I should have a better view of what the bindings
should look like, what works and what does not, and then I will have another look at automated GIR-based bindings.

### GLib reference counting

Find a clean way to integrate GLib reference counting.

### Support Webkit2gtk and gtksourceview

There is already a [WIP branch](https://github.com/vbsteven/gtk-kotlin-native/tree/wip/webkit2gtk) with [webkit2gtk-5.0](https://webkitgtk.org/)
support.

[GtkSourceView](https://wiki.gnome.org/Projects/GtkSourceView) is another library I would like to add in the near future.


## Notes on wrapping types

### Naming conventions

Most libraries in the GNOME family are based on GObject which is an object-oriented type system
written in C.

The binding modules in this project wrap the native C types into Kotlin classes using the following conventions.

The binding modules use Kotlin/Native `cinterop` to import C headers into a kotlin package prefixed with `native.<modulename>`.

* `native.gobject`
* `native.gio`
* `native.gtk`

The binding modules provide wrapped classes in a kotlin packages prefixed with `bindings.<modulename>`.

* `bindings.gobject`
* `bindings.gio`
* `bindings.gtk`

Wrapped classes and interfaces are named after their native type without prefix.
For example the Kotlin class wrapping the native `GtkButton` type is named `Button` and resides in the `bindings.gtk`
package in the `gtk-bindings` module.

### Wrapped classes

* Wrapped classes are wrappers around a C pointer to the wrapped object and don't hold any state in the class instance.
* Wrapped class implementations delegate most of the work to C methods for the wrapped type.
* Every class extends `bindings.gobject.Object` (or subclass) which holds a `CPointer<GObject>` named `gPointer` 
  pointing to the underlying native `GObject`.
* Every class exposes a typed pointer val named after their native type which casts the underlying `gPointer` to a typed pointer.
* Wrapped classes typically don't have a primary constructor.
* Wrapped classes have a constructor taking a generic `CPointer<*>` and pass the pointer up to their parent class constructor.
* Wrapped classes that can be instantiated also have a no-arg constructor or one or more utility constructors. These constructors
  typically call a native C `_new()` function (for example `gtk_label_new()`) and pass the resulting pointer to their super class.
* Wrapped classes can have Kotlin properties (var/val) and methods which should all delegate to native functions passing
  in the typed pointer as the first argument.

Wrapper classes look like this:

```kotlin
package bindings.gtk

// class named Button, wraps a GtkButton
// extends Widget, wraps a GtkWidget
// implements Actionable interface, wraps GtkActionable
open class Button : Widget, Actionable { 
    // expose typed pointer named gtkButtonPointer, casted down from the underlying gPointer
    val gtkButtonPointer get() = gPointer.asTypedPointer<GtkButton>()
  
    // expose typed pointer required by the Actionable interface
    override val gtkActionablePointer get() = gPointer.asTypedPointer<GtkActionable>()

    // constructor taking a pointer and delegating to super
    // used for wrapping pointers into a class instance
    constructor(pointer: CPointer<*>) : super(pointer)
    
    // no-arg constructor for instantiating new objects of this class
    constructor() : this(gtk_button_new()!!)

    // utility constructor for instantiating using an alternative function
    constructor(label: String) : this(gtk_button_new_with_label(label)!!)

    // a readwrite kotlin property named label
    // delegates the implementation to native C functions
    //   - passes the typed pointer as the first argument
    //   - performs argument and return type conversion
    var label: String?
        get() = gtk_button_get_label(gtkButtonPointer)?.toKString()
        set(value) = gtk_button_set_label(gtkButtonPointer, value)

    // a readwrite kotlin property named child
    // delegates the implementation to native C functions
    //  - passes the typed pointer as the first argument
    //  - performs argument and return type conversion
    var child: Widget?
        // this C function returns a widget pointer, convert to a wrapped Widget class using the asWidget extension function
        get() = gtk_button_get_child(gtkButtonPointer)?.asWidget()
        // this C function takes a widget pointer as argument, extract the typed pointer from the wrapped Widget instance
        set(value) = gtk_button_set_child(gtkButtonPointer, value?.gtkWidgetPointer)
  
    /* ...various other properties and methods... */
}

```

### Wrapped interfaces

Native interfaces are wrapped as well. Each native interface is wrapped in a Kotlin interface.

* Each wrapped interface has a typed pointer val named after the native interface type. Every class implementing the interface
  is required to provide this pointer.
* Wrapped interfaces can have Kotlin properties (var/val) and methods which should all delegate to native functions
  passing in the typed pointer as the first argument.

Wrapper interfaces look like this:

```kotlin
package bindings.gtk

// interface named Orientable, wraps the GtkOrientable interface
interface Orientable {
    // require implementing class to provide a typed pointer to the underlying object
    val gtkOrientablePointer: CPointer<GtkOrientable>

    // a readwrite kotlin property named orientation
    var orientation: GtkOrientation
        get() = gtk_orientable_get_orientation(gtkOrientablePointer)
        set(value) = gtk_orientable_set_orientation(gtkOrientablePointer, value)
}
```

## Deriving custom widget classes

Deriving custom widgets is currently possible with limited support for actions and signals. (properties coming soon)

* Declare a Kotlin class that extends `Widget` (or another class subclassed from Widget).
* Add a (primary) constructor that takes a `CPointer<*>` argument and pass the pointer to `super` constructor.
* Add a companion object that extends `WidgetCompanion<T>` where `T` is your class.
  * Override the `typeName` property with the name for your class.
  * Override the `parentType` property with the `Type` object from your parent class.
  * Override `classInit` and install any signals, actions or properties your widget provides.
* Add a secondary constructor that calls `newInstancePointer()` from your companion object and pass the resulting pointer to the primary constructor.
* (optional) The secondary constructor block can assign instance properties from your constructor arguments.

An example for a custom widget named `MyWidget` that extends the libadwaita `Bin` widget and provides an action and a signal:

```kotlin
class MyWidget(pointer: CPointer<*>) : Bin(pointer) {

    var name: String = ""

    constructor(name: String) : this(newInstancePointer()) {
        this.name = name

        val button = Button(name)
        button.actionName = "mywidgetbutton-clicked"
        
        this.child = button
    }

    private fun myButtonClickedAction() {
        emitSignal(TEST_SIGNAL)
    }

    companion object : WidgetCompanion<MyWidget>() {
        override val typeName = "MyWidget"
        override val parentType = Bin.Type

        const val TEST_SIGNAL = "test-signal"

        override fun classInit(klass: WidgetClass<MyWidget>) {
            // install the signal
            klass.installSignal(TEST_SIGNAL, G_SIGNAL_RUN_FIRST)
            // install the action with the handler
            klass.installAction("mywidgetbutton-clicked", MyWidget::myButtonClickedAction)
        }
    }
}

```

## Deriving custom object classes with properties

* Declare a Kotlin class that extends `Object` (or another class subclasses from Object).
* Add a (primary) constructor that takes a `CPointer<*>` argument and pass the pointer to `super` constructor.
* Add a companion object that extends `ObjectCompanion<T>` where `T` is your class.
  * Override the `typeName` property with the name for your class.
  * Override the `parentType` property with the `Type` object from your parent class.
  * Override `classInit` and install any signals or properties your object provides.
* Add a secondary constructor that calls `newInstancePointer()` from your companion object and pass the resulting pointer to the primary constructor.
* (optional) The secondary constructor block can assign instance properties from your constructor arguments.

An example for a custom object class named `MyPerson` with some properties:

```kotlin
private class MyPerson : Object {
    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(newInstancePointer())

    var name: String by NAME_PROPERTY
    var surname: String? by SURNAME_PROPERTY
    var age: Int by AGE_PROPERTY

    companion object : ObjectCompanion<MyPerson>() {
        override val typeName = "MyPerson"
        override val parentType = Object.Type

        private val NAME_PROPERTY = stringProperty(MyPerson::name, "name", defaultValue = "")
        private val SURNAME_PROPERTY = nullableStringProperty(MyPerson::surname, "surname", defaultValue = null)
        private val AGE_PROPERTY = intProperty(MyPerson::age, "age", "age", "A persons age", 0, 200, 0)

        override fun classInit(klass: ObjectClass<MyPerson>) {
            klass.installProperty(NAME_PROPERTY)
            klass.installProperty(SURNAME_PROPERTY)
            klass.installProperty(AGE_PROPERTY)
        }
    }
}
```

With the `MyPerson` class defined like this, you can instantiate it as you would with any regular Kotlin class and it will
act as a GObject.

* Assigning a value to the `name`, `surname` and `age` properties of the kotlin instance will update the instance property
  as well as notify on the GObject for the property.
* Assigning a value to a property by name using external g_object methods (for example through property binding) will update
  the instance properties.

```kotlin
val person = MyPerson()
person.name = "Steven" // notifies on name property
person.age = 35 // notifies on age property

person.setProperty("age", 19) // update property through gobject
println(person.age) // prints 19

// property binding works as expected
val anotherPerson = MyPerson()
person.bindProperty("age", anotherPerson, "age")
person.age = 42
println(anotherPerson.age) // prints 42

```

## Casting Object instances to user types

Some GTK API's (for example list models or list item factories) return a pointer to a `GObject` which this library wraps in 
an instance of the `Object` class. These instances cannot be simple downcasted to a user-defined class using the Kotlin `myobject as MyWidget` syntax.

Instead, these objects should be converted back to a target class instance by using the `asType` extension on `Object`.

```kotlin
val task = row.item.asType(Task.Type) // Throws on type error
// or
val task = row.item.asTypeOrNull(Task.Type) // null on type error
```

The `Type` property is available on every wrapped class and on every user-defined class that implements the `ObjectCompanion` or `WidgetCompanion` companion object.

See the [TreeListModel example](examples/gtk-examples/src/nativeMain/kotlin/treelistmodel/TreeListModelExample.kt) for more examples.

