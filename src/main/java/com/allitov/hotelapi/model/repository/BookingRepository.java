package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides methods for working with a booking entity table in a database.
 * @author allitov
 */
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
