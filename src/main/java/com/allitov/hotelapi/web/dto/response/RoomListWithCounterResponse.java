package com.allitov.hotelapi.web.dto.response;

import com.allitov.hotelapi.model.entity.Room;
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

    private Integer count;

    private List<RoomResponse> rooms = new ArrayList<>();
}
