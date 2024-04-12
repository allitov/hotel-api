package com.allitov.hotelapi.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "1")
    private Integer hotelId;

    @Schema(example = "Cool description")
    private String description;

    @Schema(example = "101")
    private Short number;

    @Schema(example = "123.45")
    private String price;

    @Schema(example = "2")
    private Short maxPeople;
}
