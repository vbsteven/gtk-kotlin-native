import bindings.adw.AboutWindow
import bindings.adw.Clamp
import bindings.adw.StatusPage
import bindings.gtk.Button
import native.gtk.GtkLicense

fun buildAboutPage() = StatusPage().apply {
    title = "About Demo"
    child = Clamp().apply {
        maximumSize = 400
        child = Button("Show About").apply {
            onClicked { showAbout() }
            addCssClass("pill")
        }
    }
}

fun showAbout() {
    val about = AboutWindow()

    about.applicationIcon = "folder-music"
    about.applicationName = "Adwaita Demo"
    about.artists = listOf("Chuck Schuldiner", "Chris Cornell", "Layne Staley")
    about.comments = "Sample comments."
    about.copyright = "Copyright 2022 - Steven Van Bael"
    about.debugInfo = "Sample debug info."
    about.debugInfoFilename = "/tmp/debug.txt"
    about.designers = listOf("Designer 1", "Designer 2")
    about.developerName = "Steven Van Bael"
    about.developers = listOf("Steven Van Bael", "Mystery Developer")
    about.documenters = listOf("Documenter 1", "Documenter 2")
    about.issueUrl = "https://github.com/vbsteven/gtk-kotlin-native/issues"
    about.license = "The License Text"
    about.licenseType = GtkLicense.GTK_LICENSE_MIT_X11
    about.releaseNotes = """
            <ul>
                <li>Cool feature</li>
                <li>Another cool feature</li>
                <li>More cool features</li>
            </ul>
        """
    about.releaseNotesVersion = "0.0.1"
    about.supportUrl = about.issueUrl
    about.translatorCredits = "Credit to the translators"
    about.version = "0.0.1"
    about.website = "https://github.com/vbsteven/gtk-kotlin-native"
    about.show()
}