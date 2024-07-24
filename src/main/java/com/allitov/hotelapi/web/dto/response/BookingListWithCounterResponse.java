package com.allitov.hotelapi.web.dto.response;

import com.allitov.hotelapi.model.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class with counter for the booking entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingListWithCounterResponse {

    private Integer count;

    private List<BookingResponse> bookings = new ArrayList<>();
}
