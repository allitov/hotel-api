package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO response class for the room entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    private Integer id;

    private Integer hotelId;

    private String description;

    private Short number;

    private String price;

    private Short maxPeople;
}
