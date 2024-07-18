package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Provides methods for working with a hotel entity table in a database.
 * @author allitov
 */
public interface HotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel> {
}
