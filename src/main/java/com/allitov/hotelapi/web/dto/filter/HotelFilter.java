package com.allitov.hotelapi.web.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The filter request class for the hotel entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilter {

    private List<Integer> id;

    private String name;

    private String description;

    private String city;

    private String address;

    private Float distanceFromCenter;

    private Float rating;

    private Integer numberOfRatings;
}
