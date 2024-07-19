package com.allitov.hotelapi.web.dto.response;

import com.allitov.hotelapi.model.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class with counter for the hotel entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelListWithCounterResponse {

    private List<HotelResponse> hotels = new ArrayList<>();

    private Integer count;
}
