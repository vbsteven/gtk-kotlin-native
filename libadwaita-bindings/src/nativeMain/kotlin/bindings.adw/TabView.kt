package bindings.adw

import bindings.gio.MenuModel
import bindings.gio.asMenuModel
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.gtk.SelectionModel
import bindings.gtk.Widget
import bindings.gtk.asSelectionModel
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.*

class TabView : Widget {
    val adwTabViewPointer get() = gtkWidgetPointer.asTypedPointer<AdwTabView>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_tab_view_new()!!)

    fun addPage(child: Widget, parent: TabPage?): TabPage =
        adw_tab_view_add_page(adwTabViewPointer, child.gtkWidgetPointer, parent?.adwTabPagePointer)!!.asTabPage()

    // TODO addShortcuts when we have AdwTabViewShortcuts

    fun append(child: Widget): TabPage = adw_tab_view_append(adwTabViewPointer, child.gtkWidgetPointer)!!.asTabPage()

    fun appendPinned(child: Widget): TabPage =
        adw_tab_view_append_pinned(adwTabViewPointer, child.gtkWidgetPointer)!!.asTabPage()

    fun closeOtherPages(page: TabPage) = adw_tab_view_close_other_pages(adwTabViewPointer, page.adwTabPagePointer)

    fun closePage(page: TabPage) = adw_tab_view_close_page(adwTabViewPointer, page.adwTabPagePointer)

    fun closePageFinish(page: TabPage, confirm: Boolean) =
        adw_tab_view_close_page_finish(adwTabViewPointer, page.adwTabPagePointer, confirm.gboolean)

    fun closePagesAfter(page: TabPage) = adw_tab_view_close_pages_after(adwTabViewPointer, page.adwTabPagePointer)

    fun closePagesBefore(page: TabPage) = adw_tab_view_close_pages_before(adwTabViewPointer, page.adwTabPagePointer)

    // TODO defaultIcon property with GIcon support
    val isTransferringPage: Boolean get() = adw_tab_view_get_is_transferring_page(adwTabViewPointer).boolean

    var menuModel: MenuModel?
        get() = adw_tab_view_get_menu_model(adwTabViewPointer)?.asMenuModel()
        set(value) = adw_tab_view_set_menu_model(adwTabViewPointer, value?.gMenuModelPointer)

    val pageCount: Int get() = adw_tab_view_get_n_pages(adwTabViewPointer)

    val pinnedPageCount: Int get() = adw_tab_view_get_n_pinned_pages(adwTabViewPointer)

    fun getPageAt(position: Int): TabPage? = adw_tab_view_get_nth_page(adwTabViewPointer, position)?.asTabPage()

    fun getPage(widget: Widget): TabPage? =
        adw_tab_view_get_page(adwTabViewPointer, widget.gtkWidgetPointer)?.asTabPage()

    fun getPagePosition(page: TabPage): Int = adw_tab_view_get_page_position(adwTabViewPointer, page.adwTabPagePointer)

    // TODO the returned selectionModel needs to be freed by the caller
    val pages: SelectionModel
        get() = adw_tab_view_get_pages(adwTabViewPointer)!!.asSelectionModel()

    val selectedPage: TabPage?
        get() = adw_tab_view_get_selected_page(adwTabViewPointer)?.asTabPage()

    // TODO shortcuts property with AdwTabViewShortcuts

    fun insert(child: Widget, position: Int): TabPage =
        adw_tab_view_insert(adwTabViewPointer, child.gtkWidgetPointer, position)!!.asTabPage()

    fun insertPinned(child: Widget, position: Int): TabPage =
        adw_tab_view_insert_pinned(adwTabViewPointer, child.gtkWidgetPointer, position)!!.asTabPage()

    fun prepend(child: Widget): TabPage =
        adw_tab_view_prepend(adwTabViewPointer, child.gtkWidgetPointer)!!.asTabPage()

    fun prependPinned(child: Widget): TabPage =
        adw_tab_view_prepend_pinned(adwTabViewPointer, child.gtkWidgetPointer)!!.asTabPage()

    // TODO removeShortcuts

    fun reorderBackward(page: TabPage): Boolean =
        adw_tab_view_reorder_backward(adwTabViewPointer, page.adwTabPagePointer).boolean

    fun reorderFirst(page: TabPage): Boolean =
        adw_tab_view_reorder_first(adwTabViewPointer, page.adwTabPagePointer).boolean

    fun reorderForward(page: TabPage): Boolean =
        adw_tab_view_reorder_forward(adwTabViewPointer, page.adwTabPagePointer).boolean

    fun reorderLast(page: TabPage): Boolean =
        adw_tab_view_reorder_last(adwTabViewPointer, page.adwTabPagePointer).boolean

    fun reorderPage(page: TabPage, position: Int): Boolean =
        adw_tab_view_reorder_page(adwTabViewPointer, page.adwTabPagePointer, position).boolean

    fun selectNextPage(): Boolean = adw_tab_view_select_next_page(adwTabViewPointer).boolean

    fun selectPreviousPage(): Boolean = adw_tab_view_select_previous_page(adwTabViewPointer).boolean

    fun setPagePinned(page: TabPage, pinned: Boolean) =
        adw_tab_view_set_page_pinned(adwTabViewPointer, page.adwTabPagePointer, pinned.gboolean)

    fun setSelectedPage(page: TabPage) = adw_tab_view_set_selected_page(adwTabViewPointer, page.adwTabPagePointer)

    // TODO setShortcuts

    fun transferPage(page: TabPage, otherView: TabView, position: Int) =
        adw_tab_view_transfer_page(adwTabViewPointer, page.adwTabPagePointer, otherView.adwTabViewPointer, position)

    // TODO signal handlers

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_TAB_VIEW, ::TabView)
    }
}