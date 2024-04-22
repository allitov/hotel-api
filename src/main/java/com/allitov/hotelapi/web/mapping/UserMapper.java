package com.allitov.hotelapi.web.mapping;

import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.hotelapi.web.dto.response.UserListResponse;
import com.allitov.hotelapi.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * The mapping interface for the user entity.
 * @author allitov
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Creates a user entity from a user request DTO and returns it.
     * @param request a user request DTO to create a user entity from.
     * @return a user entity.
     */
    User requestToEntity(UserRequest request);

    /**
     * Creates a user response DTO from a user entity and returns it.
     * @param user a user entity to create a user response DTO from.
     * @return a user response DTO.
     */
    UserResponse entityToResponse(User user);

    /**
     * Creates a user list response DTO from a list of user entities and returns it.
     * @param users a list of user entities to create a user list response DTO from.
     * @return a user list response DTO.
     */
    default UserListResponse entityListToListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream().map(this::entityToResponse).toList());

        return response;
    }
}
