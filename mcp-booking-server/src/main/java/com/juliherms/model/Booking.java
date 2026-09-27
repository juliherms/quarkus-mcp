package com.juliherms.model;

import java.time.LocalDate;

/**
 * Represents a booking for a travel package.
 *
 * @param id            Unique identifier for the booking.
 * @param customerName  Name of the customer who made the booking.
 * @param destination   Destination of the travel package.
 * @param startDate     Start date of the travel package.
 * @param endDate       End date of the travel package.
 * @param status        Current status of the booking (e.g., CONFIRMED, CANCELLED).
 * @param category      Category of the travel package (e.g., ADVENTURE, TREASURES).
 */
public record Booking(
        Long id,
        String customerName,
        String destination,
        LocalDate startDate,
        LocalDate endDate,
        BookingStatusEnum status,
        CategoryEnum category
) {}