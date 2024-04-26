package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The DTO response class for the booking entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Integer id;

    private Integer roomId;

    private Integer userId;

    private LocalDate from;

    private LocalDate to;
}
