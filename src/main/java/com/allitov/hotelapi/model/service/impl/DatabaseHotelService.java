package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.repository.HotelRepository;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.model.service.util.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * The {@link HotelService} implementation to work with a hotel entity in database.
 * @author allitov
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DatabaseHotelService implements HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Returns a {@link java.util.List} of found hotels.
     * @return a {@link java.util.List} of found hotels.
     */
    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    /**
     * Returns a hotel found by ID.
     * @param id an ID by which to find the hotel.
     * @return a hotel found by ID.
     * @throws jakarta.persistence.EntityNotFoundException if the hotel entity with the specified ID was not found.
     */
    @Override
    public Hotel findById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format(ExceptionMessage.HOTEL_BY_ID_NOT_FOUND, id))
        );
    }

    /**
     * Creates a hotel entity from the specified hotel data and returns it.
     * @param hotel a hotel data to save.
     * @return a created hotel entity.
     */
    @Override
    public Hotel create(Hotel hotel) {
        hotel.setRating(0F);
        hotel.setNumberOfRatings(0);

        return hotelRepository.save(hotel);
    }

    /**
     * Updates a hotel entity found by the specified ID and returns it.
     * @param id an ID by which to update the hotel entity.
     * @param hotel a hotel to take data from.
     * @return an updated hotel entity.
     * @throws jakarta.persistence.EntityNotFoundException if the hotel entity with the specified ID was not found.
     */
    @Override
    public Hotel updateById(Integer id, Hotel hotel) {
        Hotel foundHotel = findById(id);

        ServiceUtils.copyNonNullProperties(hotel, foundHotel);

        return hotelRepository.save(foundHotel);
    }

    /**
     * Deletes a hotel entity by the specified ID.
     * @param id an ID by which to delete the hotel entity.
     */
    @Override
    public void deleteById(Integer id) {
        hotelRepository.deleteById(id);
    }
}
