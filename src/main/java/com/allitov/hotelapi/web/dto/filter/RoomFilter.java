package com.allitov.hotelapi.web.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The filter request class for the room entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoomFilter extends AbstractFilter {

    @Schema(example = "[1, 2, 3]")
    private List<Integer> id;

    @Schema(example = "Cool room")
    private String description;

    @Schema(example = "2000.00")
    private BigDecimal minPrice;

    @Schema(example = "3000.00")
    private BigDecimal maxPrice;

    @Schema(example = "3")
    private Short maxPeople;

    @Schema(example = "2024-01-01")
    private LocalDate from;

    @Schema(example = "2024-01-31")
    private LocalDate to;

    @Schema(example = "1")
    private Integer hotelId;

    @Override
    public String toString() {
        return "RoomFilter(" +
                "id=" + id +
                ", description=" + description +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", maxPeople=" + maxPeople +
                ", from=" + from +
                ", to=" + to +
                ", hotelId=" + hotelId +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ')';
    }
}
