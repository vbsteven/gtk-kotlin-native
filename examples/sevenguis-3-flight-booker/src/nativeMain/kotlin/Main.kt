import bindings.gtk.*
import bindings.gtk.usertypes.WidgetClass
import bindings.gtk.usertypes.WidgetCompanion
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import native.gobject.G_BINDING_SYNC_CREATE
import native.gobject.G_PARAM_READWRITE
import native.gtk.GTK_DIALOG_MODAL
import native.gtk.GtkButtonsType
import native.gtk.GtkMessageType
import native.gtk.GtkOrientation.GTK_ORIENTATION_VERTICAL
import usertypes.intProperty

fun main() {
    val app = Application("io.quantus.gtkkn.sevenguis.flight-booker")
    app.onActivate {
        val window = ApplicationWindow(app).apply {
            title = "7GUIs Flight Booker"
        }

        window.child = FlightBookerWidget()
        window.show()
    }
    app.run()
    app.unref()
}

enum class BookingOption(val label: String) {
    OneWay("one-way flight"),
    Return("return-flight")
}

class FlightBookerWidget : Box(newInstancePointer()) {

    // FIXME This is a first implementation with the limited property binding we already have

    private var selectedOptionIndex: Int = BookingOption.OneWay.ordinal
        set(value) {
            field = value
            configureBookingOption(BookingOption.values()[value])
        }
    private lateinit var selectedOption: BookingOption

    private val dropDown = DropDown(BookingOption.values().map { it.label })
    private val startEntry = Entry()
    private val returnEntry = Entry()
    private val bookButton = Button("Book").apply { actionName = "book" }

    init {
        // setup box
        orientation = GTK_ORIENTATION_VERTICAL
        spacing = 10
        marginStart = 10
        marginEnd = 10
        marginTop = 10
        marginBottom = 10

        // add controls
        appendAll(
            dropDown,
            startEntry,
            returnEntry,
            bookButton
        )

        dropDown.bindProperty("selected", this, "selected-item", G_BINDING_SYNC_CREATE)

        val startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        startEntry.text = startDate
        returnEntry.text = startDate

        startEntry.onChanged(::evaluate)
        returnEntry.onChanged(::evaluate)
    }

    private fun configureBookingOption(option: BookingOption) {
        selectedOption = option
        returnEntry.sensitive = when (option) {
            BookingOption.OneWay -> false
            BookingOption.Return -> true
        }
    }

    private val dateRegex = Regex("^([0-9]{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\$")
    private fun evaluate() {
        val startDate = parseDate(startEntry.text)
        val endDate = parseDate(returnEntry.text)

        if (startDate == null) startEntry.addCssClass("error")
        else startEntry.removeCssClass("error")

        if (endDate == null) returnEntry.addCssClass("error")
        else returnEntry.removeCssClass("error")

        bookButton.sensitive = when (selectedOption) {
            BookingOption.OneWay -> startDate != null
            BookingOption.Return -> startDate != null
                    && endDate != null
                    && startDate.toEpochDays() <= endDate.toEpochDays()
        }
    }

    private fun parseDate(string: String): LocalDate? {
        return dateRegex.find(string)?.let { matchResult ->
            println("Match result: ${matchResult.groupValues}")
            LocalDate(
                matchResult.groupValues[1].toInt(),
                matchResult.groupValues[2].toInt(),
                matchResult.groupValues[3].toInt()
            )
        }
    }

    private fun book() {
        val bookingText = when (selectedOption) {
            BookingOption.OneWay -> "You have booked a one-way flight on ${startEntry.text}"
            BookingOption.Return -> "You have booked a one-way flight on ${startEntry.text} with return on ${returnEntry.text}"
        }

        MessageDialog(
            null,
            GTK_DIALOG_MODAL,
            GtkMessageType.GTK_MESSAGE_INFO,
            GtkButtonsType.GTK_BUTTONS_OK,
            bookingText
        ).apply {
            title = "Confirmation"
            onResponse { close() }
        }.show()

    }

    companion object : WidgetCompanion<FlightBookerWidget>() {
        override val typeName = "FlightBookerWidget"
        override val parentType = Box.Type

        val SelectedItemProperty = intProperty(
            FlightBookerWidget::selectedOptionIndex,
            "selected-item", null, null, 0, BookingOption.values().size - 1, 0, G_PARAM_READWRITE
        )

        override fun classInit(klass: WidgetClass<FlightBookerWidget>) {
            klass.installProperty(SelectedItemProperty)
            klass.installAction("book", FlightBookerWidget::book)
        }
    }
}

