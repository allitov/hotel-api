package com.allitov.hotelapi.web.mapping.decorator;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.BookingResponse;
import com.allitov.hotelapi.web.mapping.BookingMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The decorator for the booking mapper.
 * @author allitov
 */
@Setter
public class BookingMapperDecorator implements BookingMapper {

    @Autowired
    @Qualifier("delegate")
    protected BookingMapper delegate;

    /**
     * Creates a booking entity from a booking request DTO and returns it.
     * @param request a booking request DTO to create a booking entity from.
     * @return a booking entity.
     */
    @Override
    public Booking requestToEntity(BookingRequest request) {
        Booking booking = delegate.requestToEntity(request);
        booking.setRoom(Room.builder().id(request.getRoomId()).build());
        booking.setUser(User.builder().id(request.getUserId()).build());

        return booking;
    }

    /**
     * Creates a booking response DTO from a booking entity and returns it.
     * @param booking a booking entity to create a booking response DTO from.
     * @return a booking response DTO.
     */
    @Override
    public BookingResponse entityToResponse(Booking booking) {
        BookingResponse response = delegate.entityToResponse(booking);
        response.setRoomId(booking.getRoom().getId());
        response.setUserId(booking.getUser().getId());

        return response;
    }
}
