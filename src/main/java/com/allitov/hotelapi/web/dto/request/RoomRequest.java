package com.allitov.hotelapi.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO request class for the room entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private Integer hotelId;

    private String description;

    private Short number;

    private String price;

    private Short maxPeople;
}
