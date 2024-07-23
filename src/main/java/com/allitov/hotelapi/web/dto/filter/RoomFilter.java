package com.allitov.hotelapi.web.dto.filter;

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

    private List<Integer> id;

    private String description;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Short maxPeople;

    private LocalDate from;

    private LocalDate to;

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
