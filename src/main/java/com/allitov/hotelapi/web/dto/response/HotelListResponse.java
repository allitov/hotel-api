package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class for the hotel entity.
 * @author allitov
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelListResponse {

    private List<HotelResponse> hotels = new ArrayList<>();
}
