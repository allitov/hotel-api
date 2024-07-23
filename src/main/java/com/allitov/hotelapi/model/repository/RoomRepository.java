package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.Room;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Provides methods for working with a room entity table in a database.
 * @author allitov
 */
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"unavailableDates"})
    Optional<Room> findById(@NonNull Integer id);
}
