package com.allitov.hotelapi.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "The Best Hotel")
    private String name;

    @Schema(example = "Cool description")
    private String description;

    @Schema(example = "City")
    private String city;

    @Schema(example = "Address")
    private String address;

    @Schema(example = "1.5")
    private Float distanceFromCenter;
}
