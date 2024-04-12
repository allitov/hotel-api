package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides methods for working with a room entity table in a database.
 * @author allitov
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
