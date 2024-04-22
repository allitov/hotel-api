package com.allitov.hotelapi.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO list response class for the user entity.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {

    private List<UserResponse> users = new ArrayList<>();
}
