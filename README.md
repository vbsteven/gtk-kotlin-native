# GTK Kotlin/Native bindings

Write GTK applications using Kotlin/Native.

This project provides binding modules for GTK4 and related GNOME libraries combined with
utility modules that make it possible to write GTK desktop applications in idiomatic Kotlin.

This project **does not** provide bindings for the full GTK API surface and all of its GNOME dependencies.

The main goal is to provide enough glue for application developers familiar with Kotlin to
get started writing GTK applications without having to dig into the details of the C API.

While this projects aims to wrap/abstract the existing C API into an idiomatic(-ish) Kotlin API, it does not
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

## Current State

* `gobject-bindings` module
  * basic `GObject` support
  * basic `GVariant` support
  * **experimental** support for subclassing `GObject` in user-defined types.
* `gio-bindings` module
  * Application
  * Actions
    * Action
    * SimpleAction
  * Lists
    * ListModel
    * ListStore
* `gtk-bindings` module
  * Application and Windows
    * Application
    * Window
    * ApplicationWindow
  * Widgets
    * Widget
    * Label
    * Button
    * Box
    * MenuButton
    * ListBox
    * ListBoxRow
  * Dialogs
    * AboutDialog
    * MessageDialog
* `libadwaita-bindings` module
  * Application and Windows
    * Application
    * Window
    * ApplicationWindow
    * AboutWindow
  * Widgets
    * ActionRow
    * Avatar
    * Clamp
    * HeaderBar
    * PreferencesRow
    * StatusPage
    * TabView
    * TabBar
    * TabPage
  * Message Dialogs
  * Toasts
    * ToastOverlay
    * Toast
  * ViewStack
    * ViewStack
    * ViewStackPage
    * ViewSwitcher
    * ViewSwitcherBar
    * ViewSwitcherTitle

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



