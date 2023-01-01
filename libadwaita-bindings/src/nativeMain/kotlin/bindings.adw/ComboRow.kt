package bindings.adw

import bindings.gio.ListModel
import bindings.gio.asListModel
import bindings.gobject.*
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.sizeOf
import native.adwaita.*

open class ComboRow : ActionRow {
    val adwComboRowPointer get() = adwActionRowPointer.asTypedPointer<AdwComboRow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_combo_row_new()!!)

    // TODO add expression property with GtkExpression

    // TODO add factory property
    // TODO add listFactory property

    var model: ListModel?
        get() = adw_combo_row_get_model(adwComboRowPointer)?.asListModel()
        set(value) = adw_combo_row_set_model(adwComboRowPointer, value?.gListModelPointer)

    var selected: Int
        get() = adw_combo_row_get_selected(adwComboRowPointer).toInt()
        set(value) = adw_combo_row_set_selected(adwComboRowPointer, value.toUInt())

    val selectedItem: Object?
        get() = adw_combo_row_get_selected_item(adwComboRowPointer)?.asObject()

    var useSubtitle: Boolean
        get() = adw_combo_row_get_use_subtitle(adwComboRowPointer).boolean
        set(value) = adw_combo_row_set_use_subtitle(adwComboRowPointer, value.gboolean)

    companion object {
        val Type = BuiltinTypeInfo(
            "AdwComboRow",
            ADW_TYPE_COMBO_ROW,
            sizeOf<AdwComboRowClass>(),
            sizeOf<AdwComboRow>(),
            ::ComboRow
        )
    }
}