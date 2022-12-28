package bindings.gtk

import bindings.gtk.internal.*
import kotlinx.cinterop.CPointer
import native.gtk.*

fun CPointer<GtkAboutDialog>.asAboutDialog(): AboutDialog = AboutDialog(this)
fun CPointer<GtkActionable>.asActionable(): Actionable = ActionableWrapper(this)
fun CPointer<GtkApplication>.asApplication(): Application = Application(this)
fun CPointer<GtkApplicationWindow>.asApplicationWindow(): ApplicationWindow = ApplicationWindow(this)
fun CPointer<GtkBox>.asBox(): Box = Box(this)
fun CPointer<GtkButton>.asButton(): Button = Button(this)
fun CPointer<GtkDialog>.asDialog(): Dialog = Dialog(this)
fun CPointer<GtkEditable>.asEditable(): Editable = EditableWrapper(this)
fun CPointer<GtkHeaderBar>.asHeaderBar(): HeaderBar = HeaderBar(this)
fun CPointer<GtkLabel>.asLabel(): Label = Label(this)
fun CPointer<GtkListBox>.asListBox(): ListBox = ListBox(this)
fun CPointer<GtkListBoxRow>.asListBoxRow(): ListBoxRow = ListBoxRow(this)
fun CPointer<GtkMenuButton>.asMenuButton(): MenuButton = MenuButton(this)
fun CPointer<GtkMessageDialog>.asMessageDialog(): MessageDialog = MessageDialog(this)
fun CPointer<GtkNative>.asNative(): Native = NativeWrapper(this)
fun CPointer<GtkOrientable>.asOrientable(): Orientable = OrientableWrapper(this)
fun CPointer<GtkPopover>.asPopover(): Popover = Popover(this)
fun CPointer<GtkRoot>.asRoot(): Root = RootWrapper(this)
fun CPointer<GtkSelectionModel>.asSelectionModel(): SelectionModel = SelectionModelWrapper(this)
fun CPointer<GtkSeparator>.asSeparator(): Separator = Separator(this)
fun CPointer<GtkStack>.asStack(): Stack = Stack(this)
fun CPointer<GtkStackPage>.asStackPage(): StackPage = StackPage(this)
fun CPointer<GtkStackSidebar>.asStackSidebar(): StackSidebar = StackSidebar(this)
fun CPointer<GtkStringList>.asStringList(): StringList = StringList(this)
fun CPointer<GtkToggleButton>.asToggleButton(): ToggleButton = ToggleButton(this)
fun CPointer<GtkWidget>.asWidget(): Widget = Widget(this)
fun CPointer<GtkWindow>.asWindow(): Window = Window(this)