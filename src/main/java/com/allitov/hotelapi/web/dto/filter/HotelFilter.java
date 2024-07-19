package com.allitov.hotelapi.web.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "[1, 2, 3]")
    private List<Integer> id;

    @Schema(example = "The best hotel")
    private String name;

    @Schema(example = "Cool description")
    private String description;

    @Schema(example = "City")
    private String city;

    @Schema(example = "Address")
    private String address;

    @Schema(example = "1.5")
    private Float distanceFromCenter;

    @Schema(example = "4.9")
    private Float rating;

    @Schema(example = "299")
    private Integer numberOfRatings;
}
