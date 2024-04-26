package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.entity.UnavailableDates;
import com.allitov.hotelapi.model.repository.BookingRepository;
import com.allitov.hotelapi.model.service.BookingService;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.DateTimeException;
import java.util.List;

/**
 * The {@link BookingService} implementation to work with a booking entity in a database.
 * @author allitov
 */
@Service
@RequiredArgsConstructor
public class DatabaseBookingService implements BookingService {

    private final BookingRepository bookingRepository;

    private final RoomService roomService;

    private final UserService userService;

    /**
     * Returns a list of found bookings.
     * @return a list of found bookings.
     */
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    /**
     * Creates a booking from the specified booking data and returns it.
     * @param booking a booking data to save.
     * @return a created booking entity.
     * @throws DateTimeException if <em>from</em> date is after <em>to</em> date.
     * @throws jakarta.persistence.EntityNotFoundException if room or user with the specified id doesn't exist.
     */
    @Override
    public Booking create(Booking booking) {
        if (areDatesInvalid(booking)) {
            throw new DateTimeException(ExceptionMessage.BOOKING_INVALID_DATE);
        }
        booking.setRoom(roomService.findById(booking.getRoom().getId()));
        if (areDatesUnavailable(booking)) {
            throw new DateTimeException(MessageFormat.format(
                    ExceptionMessage.BOOKING_UNAVAILABLE_DATES,
                    booking.getFrom(), booking.getTo()));
        }
        booking.setUser(userService.findById(booking.getUser().getId()));

        return bookingRepository.save(booking);
    }

    private boolean areDatesInvalid(Booking booking) {
        return booking.getFrom().isAfter(booking.getTo());
    }

    private boolean areDatesUnavailable(Booking booking) {
        for (UnavailableDates unavailableDates : booking.getRoom().getUnavailableDates()) {
            if (!(booking.getFrom().isAfter(unavailableDates.getTo()) ||
                    booking.getTo().isBefore(unavailableDates.getFrom()))) {
                return true;
            }
        }

        return false;
    }
}
