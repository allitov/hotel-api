package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.Booking;

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
     * Creates a booking from the specified booking data and returns it.
     * @param booking a booking data to save.
     * @return a created booking entity.
     */
    Booking create(Booking booking);
}
