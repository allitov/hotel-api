package com.allitov.hotelapi.web.dto.filter;

import lombok.*;

/**
 * The filter request class for the booking entity.
 * @author allitov
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BookingFilter extends AbstractFilter {

    @Builder
    public BookingFilter(Integer pageSize, Integer pageNumber) {
        super(pageSize, pageNumber);
    }

    public BookingFilter() {
    }
}
