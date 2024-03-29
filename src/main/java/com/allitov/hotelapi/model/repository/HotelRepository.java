package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides methods for working with a hotel entity table in a database.
 * @author allitov
 * @version 1.0
 */
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

}
