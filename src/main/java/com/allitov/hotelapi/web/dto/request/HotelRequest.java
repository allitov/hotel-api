package com.allitov.hotelapi.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO request class for the hotel entity.
 * @author allitov
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    private String name;

    private String description;

    private String city;

    private String address;

    private Float distanceFromCenter;
}
