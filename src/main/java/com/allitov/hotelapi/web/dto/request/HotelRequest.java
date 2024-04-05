package com.allitov.hotelapi.web.dto.request;

import com.allitov.hotelapi.exception.ExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO request class for the hotel entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    @NotBlank(message = ExceptionMessage.HOTEL_BLANK_NAME)
    @Size(max = 50, message = ExceptionMessage.HOTEL_INVALID_NAME_LENGTH)
    @Schema(example = "The Best Hotel")
    private String name;

    @NotBlank(message = ExceptionMessage.HOTEL_BLANK_DESCRIPTION)
    @Size(max = 255, message = ExceptionMessage.HOTEL_INVALID_DESCRIPTION_LENGTH)
    @Schema(example = "Cool description")
    private String description;

    @NotBlank(message = ExceptionMessage.HOTEL_BLANK_CITY)
    @Size(max = 255, message = ExceptionMessage.HOTEL_INVALID_CITY_LENGTH)
    @Schema(example = "City")
    private String city;

    @NotBlank(message = ExceptionMessage.HOTEL_BLANK_ADDRESS)
    @Size(max = 255, message = ExceptionMessage.HOTEL_INVALID_ADDRESS_LENGTH)
    @Schema(example = "Address")
    private String address;

    @NotNull(message = ExceptionMessage.HOTEL_BLANK_DISTANCE_FROM_CENTER)
    @Schema(example = "1.5")
    private Float distanceFromCenter;
}
