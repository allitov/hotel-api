package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.RoomListResponse;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomMapperTest {

    private RoomMapper roomMapper;

    private Room room;

    @BeforeEach
    public void setup() {
        RoomMapperImpl roomMapperImpl = new RoomMapperImpl();
        roomMapperImpl.setDelegate(new RoomMapperImpl_());
        roomMapper = roomMapperImpl;

        room = Room.builder()
                .id(1)
                .description("description")
                .number((short) 101)
                .price(new BigDecimal("123.45"))
                .maxPeople((short) 5)
                .unavailableDates(List.of())
                .hotel(Hotel.builder().id(1).build())
                .build();
    }

    @Test
    @DisplayName("Test requestToEntity()")
    public void givenRoomRequest_whenRequestToEntity_thenRoom() {
        RoomRequest request = RoomRequest.builder()
                .hotelId(1)
                .description("description")
                .number((short) 101)
                .price("123.45")
                .maxPeople((short) 5)
                .build();

        Room actualRoom = roomMapper.requestToEntity(request);

        assertEquals(room, actualRoom);
    }

    @Test
    @DisplayName("Test entityToResponse()")
    public void givenRoom_whenEntityToResponse_thenRoomResponse() {
        RoomResponse response = RoomResponse.builder()
                .id(1)
                .hotelId(1)
                .description("description")
                .number((short) 101)
                .price("123.45")
                .maxPeople((short) 5)
                .build();

        RoomResponse actualRoomResponse = roomMapper.entityToResponse(room);

        assertEquals(response, actualRoomResponse);
    }

    @Test
    @DisplayName("Test entityListToListResponse()")
    public void givenRoomList_whenEntityListToListResponse_thenRoomListResponse() {
        RoomResponse roomResponse = RoomResponse.builder()
                .id(1)
                .hotelId(1)
                .description("description")
                .number((short) 101)
                .price("123.45")
                .maxPeople((short) 5)
                .build();
        RoomListResponse response = new RoomListResponse(List.of(roomResponse));

        RoomListResponse actualResponse = roomMapper.entityListToListResponse(List.of(room));

        assertEquals(response, actualResponse);
    }
}
