package com.allitov.hotelapi.web.dto.request;

import com.allitov.hotelapi.exception.ExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = ExceptionMessage.BOOKING_NULL_ROOM_ID)
    @Schema(example = "1")
    private Integer roomId;

    @NotNull(message = ExceptionMessage.BOOKING_NULL_USER_ID)
    @Schema(example = "1")
    private Integer userId;

    @NotNull(message = ExceptionMessage.BOOKING_NULL_FROM_DATE)
    @Schema(example = "2024-01-01")
    private LocalDate from;

    @NotNull(message = ExceptionMessage.BOOKING_NULL_TO_DATE)
    @Schema(example = "2024-01-31")
    private LocalDate to;
}
