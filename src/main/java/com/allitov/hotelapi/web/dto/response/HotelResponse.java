package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO response class for the hotel entity.
 * @author allitov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private Integer id;

    private String name;

    private String description;

    private String city;

    private String address;

    private Float distanceFromCenter;

    private Float rating;

    private Integer numberOfRatings;
}
