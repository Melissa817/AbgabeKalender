package com.example.aufgabe3.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aufgabe3.model.BookingEntry
import com.example.aufgabe3.viewmodel.SharedViewModel
import java.time.format.DateTimeFormatter
/**
 * HomeScreen displays a list of booking entries with a top bar and a floating action button to navigate to the add screen.
 * If no bookings are available, a message is shown.
 * @param navController The navigation controller for screen transitions.
 * @param sharedViewModel The view model managing booking entries.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val bookingsEntries by sharedViewModel.bookingsEntries.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Entries") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add booking")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (bookingsEntries.isEmpty()) {
                Text(
                    text = "No bookings available.",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(bookingsEntries) { booking ->
                        BookingEntryItem(
                            booking = booking,
                            onDeleteClick = {
                                sharedViewModel.deleteBookingEntry(booking)
                            }
                        )
                    }
                }
            }
        }
    }
}
/**
 * BookingEntryItem displays a booking entry with its details and a delete button.
 * @param booking The `BookingEntry` object with booking details.
 * @param onDeleteClick The callback to handle the delete action.
 */
@Composable
fun BookingEntryItem(
    booking: BookingEntry,
    onDeleteClick: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = booking.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${booking.arrivalDate.format(dateFormatter)} - ${booking.departureDate.format(dateFormatter)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete booking")
            }
        }
    }
}
