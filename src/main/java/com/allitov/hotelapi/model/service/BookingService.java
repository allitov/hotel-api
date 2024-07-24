package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.web.dto.filter.BookingFilter;

import java.util.List;

/**
 * Provides methods to manipulate with a booking entity data.
 * @author allitov
 */
public interface BookingService {

    /**
     * Returns a list of found bookings.
     * @return a list of found bookings.
     */
    List<Booking> findAll();

    /**
     * Returns a list of bookings that match the filtering parameters.
     * @param filter a filter to search for booking entities.
     * @return a list of found booking entities.
     */
    List<Booking> filterBy(BookingFilter filter);

    /**
     * Creates a booking from the specified booking data and returns it.
     * @param booking a booking data to save.
     * @return a created booking entity.
     */
    Booking create(Booking booking);
}
