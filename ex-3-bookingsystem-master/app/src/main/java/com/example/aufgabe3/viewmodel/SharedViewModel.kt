package com.example.aufgabe3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aufgabe3.model.BookingEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
/**
 * ViewModel for managing and sharing booking entries.
 * Uses `StateFlow` for reactive state management, allowing screens to observe and react to data changes.
 */

class SharedViewModel : ViewModel() {
    /*
    Internally used MutableStateFlow that stores the current list of booking entries.
    MutableStateFlow is a special form of Flow that manages and makes the state observable.
     */
    private val _bookingsEntries = MutableStateFlow<List<BookingEntry>>(emptyList())
    /*
    Publicly accessible, read-only state of the booking entries.
    This StateFlow variable can be observed by UI components to react to changes in the list of bookings.
     */
    val bookingsEntries: StateFlow<List<BookingEntry>> = _bookingsEntries

    /**
     * Adds a new booking entry to the list.
     * @param bookingEntry The `BookingEntry` to be added.
     * This method updates the list of booking entries by appending the new entry.
     */
    fun addBookingEntry(bookingEntry: BookingEntry) {
        _bookingsEntries.value = _bookingsEntries.value + bookingEntry
    }
    /**
     * Deletes a booking entry from the list.
     * @param bookingEntry The `BookingEntry` to be removed.
     * This method removes the specified booking entry from the list.
     */
    fun deleteBookingEntry(bookingEntry: BookingEntry) {
        _bookingsEntries.value = _bookingsEntries.value.filter { it != bookingEntry }
    }
}
