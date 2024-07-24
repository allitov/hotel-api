package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTest {

    private BookingMapper bookingMapper;

    private Booking booking;

    @BeforeEach
    public void setup() {
        BookingMapperImpl bookingMapperImpl = new BookingMapperImpl();
        bookingMapperImpl.setDelegate(new BookingMapperImpl_());
        bookingMapper = bookingMapperImpl;

        booking = Booking.builder()
                .id(1)
                .room(Room.builder().id(1).build())
                .user(User.builder().id(1).build())
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();
    }

    @Test
    @DisplayName("Test requestToEntity()")
    public void givenBookingRequest_whenRequestToEntity_thenBooking() {
        BookingRequest request = BookingRequest.builder()
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();

        Booking actualBooking = bookingMapper.requestToEntity(request);

        assertEquals(booking, actualBooking);
    }

    @Test
    @DisplayName("Test entityToResponse()")
    public void givenBooking_whenEntityToResponse_thenBookingResponse() {
        BookingResponse response = BookingResponse.builder()
                .id(1)
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();

        BookingResponse actualResponse = bookingMapper.entityToResponse(booking);

        assertEquals(response, actualResponse);
    }

    @Test
    @DisplayName("Test entityListToListResponse()")
    public void givenBookingList_whenEntityListToListResponse_thenBookingListResponse() {
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(1)
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();
        BookingListResponse response = new BookingListResponse(List.of(bookingResponse));

        BookingListResponse actualResponse = bookingMapper.entityListToListResponse(List.of(booking));

        assertEquals(response, actualResponse);
    }

    @Test
    @DisplayName("Test entityListToListWithCounterResponse()")
    public void givenHotelList_whenEntityListToListWithCounterResponse_thenHotelListWithCounterResponse() {
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(1)
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();
        BookingListWithCounterResponse response =
                new BookingListWithCounterResponse(1, List.of(bookingResponse));

        BookingListWithCounterResponse actualResponse =
                bookingMapper.entityListToListWithCounterResponse(List.of(booking));

        assertEquals(response, actualResponse);
    }
}
