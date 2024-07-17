package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.Hotel;

/**
 * Provides methods to manipulate with a hotel entity data.
 * @author allitov
 */
public interface HotelService extends CrudService<Hotel, Integer> {

    /**
     * Updates rating of the specified hotel entity.
     * @param id an id by which to find the hotel entity.
     * @param newMark a new mark to be added.
     * @return an updated hotel entity.
     */
    Hotel updateRatingById(Integer id, Integer newMark);
}
