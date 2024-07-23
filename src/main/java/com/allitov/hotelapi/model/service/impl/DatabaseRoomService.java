package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.repository.RoomRepository;
import com.allitov.hotelapi.model.repository.specification.RoomSpecification;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.model.service.util.ServiceUtils;
import com.allitov.hotelapi.web.dto.filter.RoomFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * The {@link RoomService} implementation to work with a room entity in database.
 * @author allitov
 */
@Service
@RequiredArgsConstructor
public class DatabaseRoomService implements RoomService {

    private final RoomRepository roomRepository;

    private final HotelService hotelService;

    /**
     * Returns a list of found rooms.
     * @return a list of found rooms.
     */
    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    /**
     * Returns a list of rooms that match the filtering parameters.
     * @param filter a filter to search for room entities.
     * @return a list of found room entities.
     */
    @Override
    public List<Room> filterBy(RoomFilter filter) {
        return roomRepository.findAll(RoomSpecification.withFilter(filter));
    }

    /**
     * Returns a room found by ID.
     * @param id an ID by which to find the room.
     * @return a room found by ID.
     * @throws EntityNotFoundException if the room with the specified ID was not found.
     */
    @Override
    public Room findById(Integer id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format(ExceptionMessage.ROOM_BY_ID_NOT_FOUND, id))
        );
    }

    /**
     * Creates a room from the specified room and returns it.
     * @param room a room data to save.
     * @return a created room.
     */
    @Override
    public Room create(Room room) {
        room.setHotel(hotelService.findById(room.getHotel().getId()));

        return roomRepository.save(room);
    }

    /**
     * Updates a room found by the specified ID and returns it.
     * @param id an ID by which to update the room.
     * @param room a room to take data from.
     * @return an updated room.
     * @throws EntityNotFoundException if the room with the specified ID was not found.
     */
    @Override
    public Room updateById(Integer id, Room room) {
        Room foundRoom = findById(id);
        room.setHotel(hotelService.findById(room.getHotel().getId()));

        ServiceUtils.copyNonNullProperties(room, foundRoom);

        return roomRepository.save(foundRoom);
    }

    /**
     * Deletes a room by the specified ID.
     * @param id an ID by which to delete the room.
     */
    @Override
    public void deleteById(Integer id) {
        roomRepository.deleteById(id);
    }
}
