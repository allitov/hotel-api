package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class for the room entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomListResponse {

    private List<RoomResponse> rooms = new ArrayList<>();
}
