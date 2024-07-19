package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.repository.HotelRepository;
import com.allitov.hotelapi.model.repository.specification.HotelSpecification;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.model.service.util.ServiceUtils;
import com.allitov.hotelapi.web.dto.filter.HotelFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * The {@link HotelService} implementation to work with a hotel entity in database.
 * @author allitov
 */
@Service
@RequiredArgsConstructor
public class DatabaseHotelService implements HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Returns a list of found hotels.
     * @return a list of found hotels.
     */
    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    /**
     * Returns a list of hotels that match the filtering parameters.
     * @param filter a filter to search for hotel entities.
     * @return a list of found hotel entities.
     */
    @Override
    public List<Hotel> filterBy(HotelFilter filter) {
        if (filter.getPageSize() == null || filter.getPageNumber() == null) {
            return hotelRepository.findAll(HotelSpecification.withFilter(filter));
        }

        return hotelRepository.findAll(
                HotelSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .getContent();
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

    /**
     * Updates rating of the specified hotel entity.
     * @param id an id by which to find the hotel entity.
     * @param newMark a new mark to be added.
     * @return an updated hotel entity.
     */
    @Override
    public Hotel updateRatingById(Integer id, Integer newMark) {
        if (newMark < 1 || newMark > 5) {
            throw new IllegalArgumentException(ExceptionMessage.HOTEL_ILLEGAL_RATING);
        }

        Hotel foundHotel = findById(id);
        foundHotel.setRating(calculateRating(foundHotel, newMark));
        foundHotel.setNumberOfRatings(foundHotel.getNumberOfRatings() + 1);

        return hotelRepository.save(foundHotel);
    }

    private float calculateRating(Hotel hotel, int newMark) {
        float rating = hotel.getRating();
        int numberOfRatings = hotel.getNumberOfRatings();
        float totalRating = rating * numberOfRatings;
        totalRating = totalRating - rating + newMark;
        rating = totalRating / numberOfRatings;

        int scale = 10;

        return (float) Math.round(rating * scale) / scale;
    }
}
