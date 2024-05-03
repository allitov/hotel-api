package com.allitov.hotelapi.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "1")
    private Integer id;

    @Schema(example = "1")
    private Integer roomId;

    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "2024-01-01")
    private LocalDate from;

    @Schema(example = "2024-01-31")
    private LocalDate to;
}
