package bindings.adw

import bindings.gobject.asTypedPointer
import bindings.util.toCStringList
import bindings.util.toStringList
import internal.BuiltinTypeInfo
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import native.adwaita.*
import native.gtk.GtkLicense

class AboutWindow : Window {

    val adwAboutWindowPointer get() = adwWindowPointer.asTypedPointer<AdwAboutWindow>()

    constructor(pointer: CPointer<*>) : super(pointer)

    constructor() : this(adw_about_window_new()!!)

    fun addAcknowledgementSection(
        name: String?,
        people: List<String>
    ) = memScoped {
        adw_about_window_add_acknowledgement_section(adwAboutWindowPointer, name, people.toCStringList(this))
    }

    fun addCreditSection(
        name: String?,
        people: List<String>
    ) = memScoped {
        adw_about_window_add_credit_section(adwAboutWindowPointer, name, people.toCStringList(this))
    }

    fun addLegalSection(
        title: String,
        copyright: String?,
        licenseType: GtkLicense,
        license: String?
    ) = adw_about_window_add_legal_section(adwAboutWindowPointer, title, copyright, licenseType, license)

    var applicationIcon: String
        get() = adw_about_window_get_application_icon(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_application_icon(adwAboutWindowPointer, value)

    var applicationName: String
        get() = adw_about_window_get_application_name(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_application_name(adwAboutWindowPointer, value)

    var artists: List<String>
        get() = adw_about_window_get_artists(adwAboutWindowPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            adw_about_window_set_artists(adwAboutWindowPointer, value.toCStringList(this))
        }

    var comments: String
        get() = adw_about_window_get_comments(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_comments(adwAboutWindowPointer, value)

    var copyright: String
        get() = adw_about_window_get_copyright(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_copyright(adwAboutWindowPointer, value)

    var debugInfo: String
        get() = adw_about_window_get_debug_info(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_debug_info(adwAboutWindowPointer, value)

    var debugInfoFilename: String
        get() = adw_about_window_get_debug_info_filename(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_debug_info_filename(adwAboutWindowPointer, value)

    var designers: List<String>
        get() = adw_about_window_get_designers(adwAboutWindowPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            adw_about_window_set_designers(adwAboutWindowPointer, value.toCStringList(this))
        }

    var developerName: String?
        get() = adw_about_window_get_developer_name(adwAboutWindowPointer)?.toKString()
        set(value) = adw_about_window_set_developer_name(adwAboutWindowPointer, value)

    var developers: List<String>
        get() = adw_about_window_get_developers(adwAboutWindowPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            adw_about_window_set_developers(adwAboutWindowPointer, value.toCStringList(this))
        }

    var documenters: List<String>
        get() = adw_about_window_get_documenters(adwAboutWindowPointer)?.toStringList() ?: emptyList()
        set(value) = memScoped {
            adw_about_window_set_documenters(adwAboutWindowPointer, value.toCStringList(this))
        }

    var issueUrl: String
        get() = adw_about_window_get_issue_url(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_issue_url(adwAboutWindowPointer, value)

    var license: String
        get() = adw_about_window_get_license(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_license(adwAboutWindowPointer, value)

    var licenseType: GtkLicense
        get() = adw_about_window_get_license_type(adwAboutWindowPointer)
        set(value) = adw_about_window_set_license_type(adwAboutWindowPointer, value)

    var releaseNotes: String
        get() = adw_about_window_get_release_notes(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_release_notes(adwAboutWindowPointer, value)

    var releaseNotesVersion: String
        get() = adw_about_window_get_release_notes_version(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_release_notes_version(adwAboutWindowPointer, value)

    var supportUrl: String
        get() = adw_about_window_get_support_url(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_support_url(adwAboutWindowPointer, value)

    var translatorCredits: String
        get() = adw_about_window_get_translator_credits(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_translator_credits(adwAboutWindowPointer, value)

    var version: String
        get() = adw_about_window_get_version(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_version(adwAboutWindowPointer, value)

    var website: String
        get() = adw_about_window_get_website(adwAboutWindowPointer)?.toKString() ?: ""
        set(value) = adw_about_window_set_website(adwAboutWindowPointer, value)

    companion object {
        val Type = BuiltinTypeInfo(ADW_TYPE_ABOUT_WINDOW, ::AboutWindow)
    }
}