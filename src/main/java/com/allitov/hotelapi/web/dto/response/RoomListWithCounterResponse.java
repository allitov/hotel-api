package com.allitov.hotelapi.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class with counter for the room entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomListWithCounterResponse {

    @Schema(example = "1")
    private Integer count;

    private List<RoomResponse> rooms = new ArrayList<>();
}
