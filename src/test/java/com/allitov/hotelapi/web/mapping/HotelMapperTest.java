package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelMapperTest {

    private final HotelMapper hotelMapper = new HotelMapperImpl();

    private Hotel hotel;

    @BeforeEach
    public void setup() {
        hotel = Hotel.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();
    }

    @Test
    @DisplayName("Test requestToEntity()")
    public void givenHotelRequest_whenRequestToEntity_thenHotel() {
        HotelRequest request = HotelRequest.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();

        Hotel actualHotel = hotelMapper.requestToEntity(request);

        assertEquals(hotel, actualHotel);
    }

    @Test
    @DisplayName("Test entityToResponse()")
    public void givenHotel_whenEntityToResponse_thenHotelResponse() {
        HotelResponse response = HotelResponse.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();

        HotelResponse actualResponse = hotelMapper.entityToResponse(hotel);

        assertEquals(response, actualResponse);
    }

    @Test
    @DisplayName("Test entityListToListResponse()")
    public void givenHotelList_whenEntityListToListResponse_thenHotelListResponse() {
        HotelResponse hotelResponse = HotelResponse.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();
        HotelListResponse response = new HotelListResponse(List.of(hotelResponse));

        HotelListResponse actualResponse = hotelMapper.entityListToListResponse(List.of(hotel));

        assertEquals(response, actualResponse);
    }

    @Test
    @DisplayName("Test entityListToListWithCounterResponse()")
    public void givenHotelList_whenEntityListToListWithCounterResponse_thenHotelListWithCounterResponse() {
        HotelResponse hotelResponse = HotelResponse.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();
        HotelListWithCounterResponse response = new HotelListWithCounterResponse(1, List.of(hotelResponse));

        HotelListWithCounterResponse actualResponse = hotelMapper.entityListToListWithCounterResponse(List.of(hotel));

        assertEquals(response, actualResponse);
    }
}
