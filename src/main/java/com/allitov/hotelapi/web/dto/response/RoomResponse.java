package com.allitov.hotelapi.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO response class for the room entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {

    @Schema(example = "1")
    private Integer id;

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
