package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.BookingListResponse;
import com.allitov.hotelapi.web.dto.response.BookingResponse;
import com.allitov.hotelapi.web.mapping.decorator.BookingMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * The mapping interface for the booking entity.
 * @author allitov
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(BookingMapperDecorator.class)
public interface BookingMapper {

    /**
     * Creates a booking entity from a booking request DTO and returns it.
     * @param request a booking request DTO to create a booking entity from.
     * @return a booking entity.
     */
    Booking requestToEntity(BookingRequest request);

    /**
     * Creates a booking response DTO from a booking entity and returns it.
     * @param booking a booking entity to create a booking response DTO from.
     * @return a booking response DTO.
     */
    BookingResponse entityToResponse(Booking booking);

    /**
     * Creates a booking list response DTO from a list of booking entities and returns it.
     * @param bookings a list of booking entities to create a booking list response DTO from.
     * @return a booking list response DTO.
     */
    default BookingListResponse entityListToListResponse(List<Booking> bookings) {
        BookingListResponse response = new BookingListResponse();
        response.setBookings(bookings.stream().map(this::entityToResponse).toList());

        return response;
    }
}
