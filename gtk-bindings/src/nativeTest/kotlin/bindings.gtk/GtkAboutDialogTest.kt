package bindings.gtk

import bindings.gtk.testutils.GtkTestBase
import native.gtk.GtkLicense
import kotlin.test.Test

class GtkAboutDialogTest : GtkTestBase() {

    @Test
    fun testAboutDialog() = runTestApplication {
        val dialog = AboutDialog()

        dialog.application = it.app

        dialog.copyright = "Copyright 2022"
        dialog.programName = "Test Program"
        dialog.license = "LICENSE TEXT"
        dialog.licenseType = GtkLicense.GTK_LICENSE_MIT_X11
        dialog.version = "0.0.1"
        dialog.website = "https://stevenvanbael.com"
        dialog.websiteLabel = "Project website"

        dialog.addCreditSection("Credits", "Steven", "Erika", "Lore")
        dialog.addCreditSection("Secondary Credits", "Jan", "Piet", "Korneel")

        dialog.artists = listOf("Chuck Schuldiner", "Chris Cornell", "Layne Staley")
        println("Artists: ${dialog.artists}")

        dialog.comments = "Some comments"
        dialog.documenters = listOf("Documenter 1", "Documenter 2")
        dialog.logoIconName = "open-menu"
        dialog.systemInformation = "Arch Linux"
        dialog.translatorCredits = "A translator"

        dialog.present()
    }

}