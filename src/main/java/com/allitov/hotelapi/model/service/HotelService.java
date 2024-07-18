package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.web.dto.filter.HotelFilter;

import java.util.List;

/**
 * Provides methods to manipulate with a hotel entity data.
 * @author allitov
 */
public interface HotelService extends CrudService<Hotel, Integer> {

    /**
     * Returns a list of hotels that match the filtering parameters.
     * @param filter a filter to search for hotel entities.
     * @return a list of found hotel entities.
     */
    List<Hotel> filterBy(HotelFilter filter);

    /**
     * Updates rating of the specified hotel entity.
     * @param id an id by which to find the hotel entity.
     * @param newMark a new mark to be added.
     * @return an updated hotel entity.
     */
    Hotel updateRatingById(Integer id, Integer newMark);
}
