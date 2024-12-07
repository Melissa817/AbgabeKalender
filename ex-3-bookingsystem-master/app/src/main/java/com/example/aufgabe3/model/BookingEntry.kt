package com.example.aufgabe3.model

import java.time.LocalDate
/**
 * Represents a booking entry with a name and date range (arrival and departure).
 * @property arrivalDate The start date of the booking.
 * @property departureDate The end date of the booking.
 * @property name The name associated with the booking.
 */
data class BookingEntry(
    val arrivalDate: LocalDate,
    val departureDate: LocalDate,
    val name: String
)
