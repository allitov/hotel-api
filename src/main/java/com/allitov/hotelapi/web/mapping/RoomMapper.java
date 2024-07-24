package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.RoomListResponse;
import com.allitov.hotelapi.web.dto.response.RoomListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import com.allitov.hotelapi.web.mapping.decorator.RoomMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * The mapping interface for the room entity.
 * @author allitov
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(RoomMapperDecorator.class)
public interface RoomMapper {

    /**
     * Creates a room entity from a room request DTO and returns it.
     * @param request a room request DTO to create a room entity from.
     * @return a room entity.
     */
    Room requestToEntity(RoomRequest request);

    /**
     * Creates a room response DTO from a room entity and returns it.
     * @param room a room entity to create a room response DTO from.
     * @return a room response DTO.
     */
    RoomResponse entityToResponse(Room room);

    /**
     * Creates a room list response DTO from a list of room entities and returns it.
     * @param rooms a list of room entities to create a room list response DTO from.
     * @return a room list response DTO.
     */
    default RoomListResponse entityListToListResponse(List<Room> rooms) {
        RoomListResponse response = new RoomListResponse();
        response.setRooms(rooms.stream().map(this::entityToResponse).toList());

        return response;
    }

    /**
     * Creates a room list with counter response DTO from a list of room entities and returns it.
     * @param rooms a list of room entities to create a room list with counter response DTO from.
     * @return a room list with counter response DTO.
     */
    default RoomListWithCounterResponse entityListToListWithCounterResponse(List<Room> rooms) {
        RoomListWithCounterResponse response = new RoomListWithCounterResponse();
        response.setCount(rooms.size());
        response.setRooms(rooms.stream().map(this::entityToResponse).toList());

        return response;
    }
}
