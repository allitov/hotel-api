package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.web.dto.filter.RoomFilter;

import java.util.List;

/**
 * Provides methods to manipulate with a room entity data.
 * @author allitov
 */
public interface RoomService extends CrudService<Room, Integer> {

    /**
     * Returns a list of rooms that match the filtering parameters.
     * @param filter a filter to search for room entities.
     * @return a list of found room entities.
     */
    List<Room> filterBy(RoomFilter filter);
}
