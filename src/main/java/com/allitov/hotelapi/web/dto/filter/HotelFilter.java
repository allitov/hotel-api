package com.allitov.hotelapi.web.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * The filter request class for the hotel entity.
 * @author allitov
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HotelFilter extends AbstractFilter {

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

    @Override
    public String toString() {
        return "HotelFilter(" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", city=" + city +
                ", address=" + address +
                ", distanceFromCenter=" + distanceFromCenter +
                ", rating=" + rating +
                ", numberOfRatings=" + numberOfRatings +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ')';
    }
}
