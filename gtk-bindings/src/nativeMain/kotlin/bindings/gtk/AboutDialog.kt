package bindings.gtk

import bindings.gobject.ObjectCompanion
import bindings.gobject.asTypedPointer
import bindings.gobject.boolean
import bindings.gobject.gboolean
import bindings.util.toCStringList
import bindings.util.toStringList
import internal.BuiltinTypeInfo
import kotlinx.cinterop.*
import native.gtk.*

class AboutDialog : Window {

    val gtkAboutDialogPointer get() = gtkWindowPointer.asTypedPointer<GtkAboutDialog>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : super(gtk_about_dialog_new()!!)

    var copyright: String?
        get() = gtk_about_dialog_get_copyright(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_copyright(gtkAboutDialogPointer, value)

    var programName: String?
        get() = gtk_about_dialog_get_program_name(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_program_name(gtkAboutDialogPointer, value)

    var license: String?
        get() = gtk_about_dialog_get_license(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_license(gtkAboutDialogPointer, value)

    var licenseType: GtkLicense?
        get() = gtk_about_dialog_get_license_type(gtkAboutDialogPointer)
        set(value) = gtk_about_dialog_set_license_type(gtkAboutDialogPointer, value!!)

    var version: String?
        get() = gtk_about_dialog_get_version(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_version(gtkAboutDialogPointer, value)

    var website: String?
        get() = gtk_about_dialog_get_website(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_website(gtkAboutDialogPointer, value)

    var websiteLabel: String?
        get() = gtk_about_dialog_get_website_label(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_website_label(gtkAboutDialogPointer, value)

    var isWrapLicense: Boolean
        get() = gtk_about_dialog_get_wrap_license(gtkAboutDialogPointer).boolean
        set(value) = gtk_about_dialog_set_wrap_license(gtkAboutDialogPointer, value.gboolean)

    fun addCreditSection(sectionName: String, vararg people: String) = memScoped {
        val peopleWithNull = people.asList() + null
        val array = allocArrayOf(peopleWithNull.map { it?.cstr?.getPointer(this) })
        gtk_about_dialog_add_credit_section(gtkAboutDialogPointer, sectionName, array)
    }

    var artists: List<String>
        get() = gtk_about_dialog_get_artists(gtkAboutDialogPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            gtk_about_dialog_set_artists(gtkAboutDialogPointer, value.toCStringList(this))
        }

    var comments: String?
        get() = gtk_about_dialog_get_comments(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_comments(gtkAboutDialogPointer, value)

    var documenters: List<String>
        get() = gtk_about_dialog_get_documenters(gtkAboutDialogPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            gtk_about_dialog_set_documenters(gtkAboutDialogPointer, value.toCStringList(this))
        }

    // TODO logo property with GdkPaintable

    var logoIconName: String?
        get() = gtk_about_dialog_get_logo_icon_name(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_logo_icon_name(gtkAboutDialogPointer, value)

    var systemInformation: String?
        get() = gtk_about_dialog_get_system_information(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_system_information(gtkAboutDialogPointer, value)

    var translatorCredits: String?
        get() = gtk_about_dialog_get_translator_credits(gtkAboutDialogPointer)?.toKString()
        set(value) = gtk_about_dialog_set_translator_credits(gtkAboutDialogPointer, value)


    companion object : ObjectCompanion<AboutDialog>(AboutDialogTypeInfo)
}

private val AboutDialogTypeInfo = BuiltinTypeInfo(
    "GtkAboutDialog",
    GTK_TYPE_ABOUT_DIALOG,
    -1,
    -1,
    ::AboutDialog
)


