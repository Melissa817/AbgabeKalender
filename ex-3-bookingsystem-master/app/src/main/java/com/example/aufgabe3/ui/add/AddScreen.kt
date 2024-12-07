package com.example.aufgabe3.ui.add

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aufgabe3.viewmodel.SharedViewModel
import com.example.aufgabe3.model.BookingEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * AddScreen is the screen for adding a new booking entry.
 * This composable provides a form for entering the name, arrival date, and departure date of a booking.
 * A date range picker dialog allows users to select the booking dates. It also includes a "Save" button
 * to save the booking and a back navigation button.
 * @param navController The navigation controller used for navigating between screens.
 * @param sharedViewModel The shared view model used for managing the list of booking entries.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    var name by remember { mutableStateOf("") }
    var arrivalDate by remember { mutableStateOf<LocalDate?>(null) }
    var departureDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDateRangePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Booking Entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = if (arrivalDate != null && departureDate != null) {
                    "${arrivalDate!!.format(dateFormatter)} - ${departureDate!!.format(dateFormatter)}"
                } else {
                    ""
                },
                onValueChange = {},
                label = { Text("Select Date Range") },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDateRangePicker = true },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && arrivalDate != null && departureDate != null) {
                        sharedViewModel.addBookingEntry(BookingEntry(arrivalDate!!, departureDate!!, name))
                        navController.popBackStack()
                    } else {
                        errorMessage = "Please fill all the fields!"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }

    if (showDateRangePicker) {
        DateRangePickerModal(
            onDismiss = { showDateRangePicker = false },
            onDateSelected = { startDate, endDate ->
                arrivalDate = startDate
                departureDate = endDate
                showDateRangePicker = false
            }
        )
    }
}
/**
 * DateRangePickerModal is a custom dialog for selecting a date range.
 * It allows selecting a start and end date, with the end date restricted to be after or equal to the start date.
 * @param onDismiss The callback when the dialog is dismissed.
 * @param onDateSelected The callback with the selected start and end dates.
 */
@Composable
fun DateRangePickerModal(
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate, LocalDate) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    if (startDate == null) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                startDate = LocalDate.of(year, month + 1, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    } else if (endDate == null) {
        val startDateMillis = Calendar.getInstance().apply {
            set(startDate!!.year, startDate!!.monthValue - 1, startDate!!.dayOfMonth)
        }.timeInMillis

        val endDatePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                endDate = LocalDate.of(year, month + 1, dayOfMonth)
                if (startDate != null && endDate != null) {
                    onDateSelected(startDate!!, endDate!!)
                }
                onDismiss()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        endDatePicker.datePicker.minDate = startDateMillis
        endDatePicker.show()
    }
}
