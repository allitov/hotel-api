package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.UnavailableDates;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides methods for working with an unavailable dates entity table in a database.
 * @author allitov
 */
public interface UnavailableDatesRepository extends JpaRepository<UnavailableDates, Integer> {
}
