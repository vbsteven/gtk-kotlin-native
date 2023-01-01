package bindings.adw

import bindings.gobject.asTypedPointer
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import native.adwaita.ADW_TYPE_PASSWORD_ENTRY_ROW
import native.adwaita.AdwPasswordEntryRow
import native.adwaita.adw_password_entry_row_new

class PasswordEntryRow : EntryRow {
    val adwPasswordEntryRow get() = adwEntryRowPointer.asTypedPointer<AdwPasswordEntryRow>()

    constructor(pointer: CPointer<*>) : super(pointer)
    constructor() : this(adw_password_entry_row_new()!!)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_PASSWORD_ENTRY_ROW, ::PasswordEntryRow)
    }
}