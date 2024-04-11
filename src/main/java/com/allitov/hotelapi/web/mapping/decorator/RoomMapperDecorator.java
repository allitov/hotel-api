package com.allitov.hotelapi.web.mapping.decorator;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import com.allitov.hotelapi.web.mapping.RoomMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * The decorator for the room mapper.
 * @author allitov
 */
@Setter
public abstract class RoomMapperDecorator implements RoomMapper {

    @Autowired
    @Qualifier("delegate")
    protected RoomMapper delegate;

    /**
     * Creates a room entity from a room request DTO and returns it.
     * @param request a room request DTO to create a room entity from.
     * @return a room entity.
     */
    @Override
    public Room requestToEntity(RoomRequest request) {
        Room room = delegate.requestToEntity(request);
        room.setHotel(Hotel.builder().id(request.getHotelId()).build());

        return room;
    }

    /**
     * Creates a room response DTO from a room entity and returns it.
     * @param room a room entity to create a room response DTO from.
     * @return a room response DTO.
     */
    @Override
    public RoomResponse entityToResponse(Room room) {
        RoomResponse response = delegate.entityToResponse(room);
        response.setHotelId(room.getHotel().getId());

        return response;
    }
}
