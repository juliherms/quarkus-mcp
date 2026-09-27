package com.juliherms.service;

import com.juliherms.model.Booking;
import com.juliherms.model.BookingStatusEnum;
import com.juliherms.model.CategoryEnum;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing bookings.
 * This class provides methods to find, retrieve, and cancel bookings.
 */
@ApplicationScoped
public class BookingService {

    private final Map<Long, Booking> bookings = new HashMap<>();

    public BookingService() {
        bookings.put(12345L, new Booking(12345L, "John Doe", "Tesouros do Egito",
                LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(2).plusDays(10), BookingStatusEnum.CONFIRMED, CategoryEnum.TREASURES));
        bookings.put(67890L, new Booking(67890L, "Jane Smith", "Aventura Amazônia",
                LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(3).plusDays(7), BookingStatusEnum.CONFIRMED, CategoryEnum.ADVENTURE));
        bookings.put(98765L, new Booking(98765L, "Peter Jones", "Trilha Inca",
                LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(4).plusDays(8), BookingStatusEnum.CONFIRMED, CategoryEnum.ADVENTURE));
    }

    public List<Booking> findPackagesByCategory(CategoryEnum category) {
        return bookings.values().stream()
                .filter(booking -> category.equals(booking.category()))
                .toList();
    }

    public Optional<Booking> getBookingDetails(long bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public Optional<Booking> cancelBooking(long bookingId, String name) {
        if (bookings.containsKey(bookingId)) {
            Booking booking = bookings.get(bookingId);
            if (booking.customerName().equals(name)) {
                Booking cancelledBooking = new Booking(
                        booking.id(),
                        booking.customerName(),
                        booking.destination(),
                        booking.startDate(),
                        booking.endDate(),
                        BookingStatusEnum.CANCELLED,
                        booking.category()
                );
                this.bookings.replace(bookingId, cancelledBooking);
                return Optional.of(cancelledBooking);
            }
        }
        return Optional.empty();
    }
}
