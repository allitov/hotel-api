package com.allitov.hotelapi.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The DTO request class for the booking entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private Integer roomId;

    private Integer userId;

    private LocalDate from;

    private LocalDate to;
}
