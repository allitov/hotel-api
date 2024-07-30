package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.message.BookingCreationMessage;
import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.entity.UnavailableDates;
import com.allitov.hotelapi.model.repository.BookingRepository;
import com.allitov.hotelapi.model.repository.UnavailableDatesRepository;
import com.allitov.hotelapi.model.service.BookingService;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.web.dto.filter.BookingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
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

    private final UnavailableDatesRepository unavailableDatesRepository;

    private final KafkaTemplate<String, BookingCreationMessage> kafkaTemplate;

    @Value("${app.kafka.topic.booking-creation}")
    private String topicName;

    /**
     * Returns a list of found bookings.
     * @return a list of found bookings.
     */
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    /**
     * Returns a list of bookings that match the filtering parameters.
     * @param filter a filter to search for booking entities.
     * @return a list of found booking entities.
     */
    @Override
    public List<Booking> filterBy(BookingFilter filter) {
        if (filter.getPageSize() == null || filter.getPageNumber() == null) {
            return findAll();
        }

        return bookingRepository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent();
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
        booking.setUser(userService.findById(booking.getUser().getId()));
        if (areDatesUnavailable(booking)) {
            throw new DateTimeException(MessageFormat.format(
                    ExceptionMessage.BOOKING_UNAVAILABLE_DATES,
                    booking.getFrom(), booking.getTo()));
        }
        unavailableDatesRepository.save(createUnavailableDatesFromBooking(booking));
        Booking createdBooking = bookingRepository.save(booking);

        BookingCreationMessage message = BookingCreationMessage.builder()
                .userId(booking.getUser().getId())
                .from(booking.getFrom())
                .to(booking.getTo())
                .build();
        kafkaTemplate.send(topicName, message);

        return createdBooking;
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

    private UnavailableDates createUnavailableDatesFromBooking(Booking booking) {
        return UnavailableDates.builder()
                .room(booking.getRoom())
                .from(booking.getFrom())
                .to(booking.getTo())
                .build();
    }
}
