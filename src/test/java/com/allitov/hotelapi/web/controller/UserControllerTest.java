package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.hotelapi.web.dto.response.UserListResponse;
import com.allitov.hotelapi.web.dto.response.UserResponse;
import com.allitov.hotelapi.web.mapping.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {

    private final String baseUri = "/api/v1/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    // methods tests

    @Test
    @DisplayName("Test findAll() status 200")
    public void givenVoid_whenFindAll_thenUserListResponse() throws Exception {
        List<User> foundUsers = List.of();
        Mockito.when(userService.findAll())
                .thenReturn(foundUsers);
        Mockito.when(userMapper.entityListToListResponse(foundUsers))
                .thenReturn(new UserListResponse());

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'users': []}"));

        Mockito.verify(userService, Mockito.times(1))
                .findAll();
        Mockito.verify(userMapper, Mockito.times(1))
                .entityListToListResponse(foundUsers);
    }

    @Test
    @DisplayName("Test findById() status 200")
    public void givenId_whenFindById_thenUserResponse() throws Exception {
        Integer id = 1;
        User foundUser = new User();
        UserResponse response = createUserResponse();
        Mockito.when(userService.findById(id))
                .thenReturn(foundUser);
        Mockito.when(userMapper.entityToResponse(foundUser))
                .thenReturn(response);

        mockMvc.perform(get(baseUri + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1, " +
                        "'username': 'username', " +
                        "'email': 'email@email.com', " +
                        "'role': 'USER'}"));

        Mockito.verify(userService, Mockito.times(1))
                .findById(id);
        Mockito.verify(userMapper, Mockito.times(1))
                .entityToResponse(foundUser);
    }

    @Test
    @DisplayName("Test findById() status 404")
    public void givenNonexistentId_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 10;
        Mockito.when(userService.findById(id))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(get(baseUri + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(userService, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test create status 201")
    public void givenUserRequest_whenCreate_thenVoid() throws Exception {
        UserRequest request = createUserRequest();
        Integer id = 1;
        User user = new User();
        user.setId(id);
        Mockito.when(userMapper.requestToEntity(request))
                .thenReturn(user);
        Mockito.when(userService.create(user))
                .thenReturn(user);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/" + id));

        Mockito.verify(userMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(userService, Mockito.times(1))
                .create(user);
    }

    @Test
    @DisplayName("Test create() status 400")
    public void givenInvalidUserRequest_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setUsername(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Username must be specified.'}"));
    }

    @Test
    @DisplayName("Test create() status 409")
    public void givenExistingUserRequestUsername_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        User user = new User();
        Mockito.when(userMapper.requestToEntity(request))
                .thenReturn(user);
        Mockito.when(userService.create(user))
                .thenThrow(new EntityExistsException("Entity already exists."));

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity already exists.'}"));

        Mockito.verify(userMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(userService, Mockito.times(1))
                .create(user);
    }

    @Test
    @DisplayName("Test updateById() status 204")
    public void givenIdAndUserRequest_whenUpdateById_thenVoid() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        User user = new User();
        Mockito.when(userMapper.requestToEntity(request))
                .thenReturn(user);
        Mockito.when(userService.updateById(id, user))
                .thenReturn(user);

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(userMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(userService, Mockito.times(1))
                .updateById(id, user);
    }

    @Test
    @DisplayName("Test updateById() status 400")
    public void givenIdAndInvalidUserRequest_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        request.setUsername(null);

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Username must be specified.'}"));
    }

    @Test
    @DisplayName("Test updateById() status 404")
    public void givenNonexistentIdAndUserRequest_whenUpdateBuId_thenErrorResponse() throws Exception {
        Integer id = 10;
        UserRequest request = createUserRequest();
        User user = new User();
        Mockito.when(userMapper.requestToEntity(request))
                .thenReturn(user);
        Mockito.when(userService.updateById(id, user))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(userMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(userService, Mockito.times(1))
                .updateById(id, user);
    }

    @Test
    @DisplayName("Test updateById() status 409")
    public void givenIdAndExistingUserRequestUsername_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        User user = new User();
        Mockito.when(userMapper.requestToEntity(request))
                .thenReturn(user);
        Mockito.when(userService.updateById(id, user))
                .thenThrow(new EntityExistsException("Entity already exists."));

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity already exists.'}"));

        Mockito.verify(userMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(userService, Mockito.times(1))
                .updateById(id, user);
    }

    @Test
    @DisplayName("Test deleteById() status 204")
    public void givenId_whenDeleteById_thenVoid() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1))
                .deleteById(id);
    }

    // validation tests

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test UserRequest validation with blank username")
    public void givenBlankUserRequestUsername_whenCreate_thenErrorResponse(String username) throws Exception {
        UserRequest request = createUserRequest();
        request.setUsername(username);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Username must be specified.'}"));
    }

    @Test
    @DisplayName("Test UserRequest validation with null email")
    public void givenBullUserRequestEmail_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setEmail(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Email must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidEmails")
    @DisplayName("Test UserRequest validation with invalid email")
    public void givenInvalidUserRequestEmail_whenCreate_thenErrorResponse(String email) throws Exception {
        UserRequest request = createUserRequest();
        request.setEmail(email);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Email must be a valid email address.'}"));
    }

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test UserRequest validation with blank password")
    public void givenBlankUserRequestPassword_whenCreate_thenErrorResponse(String password) throws Exception {
        UserRequest request = createUserRequest();
        request.setPassword(password);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Password must be specified.'}"));
    }

    @Test
    @DisplayName("Test UserRequest validation with null role")
    public void givenNullUserRequestRole_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setRole(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Role must be specified.'}"));
    }

    @Test
    @DisplayName("Test UserRequest validation with invalid role")
    public void givenInvalidUserRequestRole_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setRole("INVALID_ROLE");

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': \"Role must be any of ['USER', 'ADMIN'].\"}"));
    }

    private UserResponse createUserResponse() {
        return UserResponse.builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .role("USER")
                .build();
    }

    private UserRequest createUserRequest() {
        return UserRequest.builder()
                .username("username")
                .email("email@email.com")
                .password("password")
                .role("USER")
                .build();
    }

    private static Stream<Arguments> createBlankStrings() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("     "),
                Arguments.of("\n\n  \n")
        );
    }

    private static Stream<Arguments> createInvalidEmails() {
        return Stream.of(
                Arguments.of("email@"),
                Arguments.of("email.com"),
                Arguments.of("@email.com")
        );
    }
}
