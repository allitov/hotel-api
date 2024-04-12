package com.allitov.hotelapi.web.dto.request;

import com.allitov.hotelapi.exception.ExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO request class for the room entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotNull(message = ExceptionMessage.ROOM_NULL_HOTEL_ID)
    @Schema(example = "1")
    private Integer hotelId;

    @NotBlank(message = ExceptionMessage.ROOM_BLANK_DESCRIPTION)
    @Size(max = 255, message = ExceptionMessage.ROOM_INVALID_DESCRIPTION_LENGTH)
    @Schema(example = "Cool description")
    private String description;

    @NotNull(message = ExceptionMessage.ROOM_NULL_NUMBER)
    @Schema(example = "101")
    private Short number;

    @NotNull(message = ExceptionMessage.ROOM_NULL_PRICE)
    @Pattern(regexp = "^[0-9]{1,10}\\.[0-9]{2}$", message = ExceptionMessage.ROOM_INVALID_PRICE)
    @Schema(example = "123.45")
    private String price;

    @NotNull(message = ExceptionMessage.ROOM_NULL_MAX_PEOPLE)
    @Positive(message = ExceptionMessage.ROOM_INVALID_MAX_PEOPLE)
    @Schema(example = "2")
    private Short maxPeople;
}
