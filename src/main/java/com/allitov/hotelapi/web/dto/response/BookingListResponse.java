package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class for the booking entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingListResponse {

    private List<BookingResponse> bookings = new ArrayList<>();
}
