package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.hotelapi.web.dto.response.UserListResponse;
import com.allitov.hotelapi.web.dto.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .password("password")
                .role(User.RoleType.USER)
                .build();
    }

    @Test
    @DisplayName("Test requestToEntity()")
    public void givenUserRequest_whenRequestToEntity_thenUser() {
        UserRequest request = UserRequest.builder()
                .username("username")
                .email("email@email.com")
                .password("password")
                .role("USER")
                .build();

        User actualUser = userMapper.requestToEntity(request);

        assertEquals(user, actualUser);
    }

    @Test
    @DisplayName("Test entityToResponse()")
    public void givenUser_whenEntityToResponse_thenUserResponse() {
        UserResponse response = UserResponse.builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .role("USER")
                .build();

        UserResponse actualResponse = userMapper.entityToResponse(user);

        assertEquals(response, actualResponse);
    }

    @Test
    @DisplayName("Test entityListToListResponse()")
    public void givenUserList_whenEntityListToListResponse_thenUserListResponse() {
        UserResponse userResponse = UserResponse.builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .role("USER")
                .build();
        UserListResponse response = new UserListResponse(List.of(userResponse));

        UserListResponse actualResponse = userMapper.entityListToListResponse(List.of(user));

        assertEquals(response, actualResponse);
    }
}
