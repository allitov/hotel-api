package com.allitov.hotelapi.web.mapping.decorator;

import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import com.allitov.hotelapi.web.mapping.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The decorator for the room mapper.
 * @author allitov
 */
public abstract class RoomMapperDecorator implements RoomMapper {

    @Autowired
    protected RoomMapper delegate;

    @Autowired
    private HotelService hotelService;

    /**
     * Creates a room entity from a room request DTO and returns it.
     * @param request a room request DTO to create a room entity from.
     * @return a room entity.
     */
    @Override
    public Room requestToEntity(RoomRequest request) {
        Room room = delegate.requestToEntity(request);
        room.setHotel(hotelService.findById(request.getHotelId()));

        return room;
    }

    /**
     * Creates a room response DTO from a room entity and returns it.
     * @param room a room entity to create a room response DTO from.
     * @return a room response DTO.
     */
    @Override
    public RoomResponse entityToResponse(Room room) {
        return delegate.entityToResponse(room);
    }
}
