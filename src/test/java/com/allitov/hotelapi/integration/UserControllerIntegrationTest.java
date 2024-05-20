package com.allitov.hotelapi.integration;

import com.allitov.hotelapi.model.repository.UserRepository;
import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.testutils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerIntegrationTest extends AbstractIntegrationTest{

    private final String baseUri = "/api/v1/user";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Test findAll() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleAdmin_whenFindAll_thenRoomListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_list_response.json");

        String actualResponse = mockMvc.perform(get(baseUri))
                                    .andExpect(status().isOk())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findAll() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenRoleAnonymous_whenFindAll_thenErrorResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(get(baseUri))
                                    .andExpect(status().isUnauthorized())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findAll() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleUser_whenFindAll_thenErrorResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(get(baseUri))
                                    .andExpect(status().isForbidden())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findById() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleAdmin_whenFindById_thenUserResponse() throws Exception {
        Integer id = 2;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isOk())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndRoleAnonymous_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 2;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isUnauthorized())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleUser_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 2;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isForbidden())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findById() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentIdAndRoleAdmin_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 1000;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_by_id_not_found_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isNotFound())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 201")
    @WithAnonymousUser
    public void givenUserRequestAndRoleAnonymous_whenCreate_thenVoid() throws Exception {
        UserRequest request = createUserRequest();
        assertEquals(2, userRepository.count());

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/3"));

        assertEquals(3, userRepository.count());
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 400")
    @WithAnonymousUser
    public void givenInvalidUserRequestAndRoleAnonymous_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setUsername(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_request_blank_name_response.json");
        assertEquals(2, userRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(2, userRepository.count());
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 409")
    @WithAnonymousUser
    public void givenExistingUserRequestUsernameAndRoleAnonymous_whenCreate_thenErrorResponse() throws Exception {
        UserRequest request = createUserRequest();
        request.setUsername("admin");
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_username_already_exists_response.json");
        assertEquals(2, userRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isConflict())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(2, userRepository.count());
    }

    @Test
    @DisplayName("Test updateById() role USER status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndUserRequestAndRoleUser_whenUpdateById_thenVoid() throws Exception {
        Integer id = 2;
        UserRequest request = createUserRequest();
        assertEquals("user", userService.findById(id).getUsername());

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        assertEquals("new_user", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndUserRequestAndRoleAdmin_whenUpdateById_thenVoid() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        assertEquals("admin", userService.findById(id).getUsername());

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        assertEquals("new_user", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndInvalidUserRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        request.setUsername(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_request_blank_name_response.json");
        assertEquals("admin", userService.findById(id).getUsername());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("admin", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndUserRequestAndRoleAnonymous_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 2;
        UserRequest request = createUserRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals("user", userService.findById(id).getUsername());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("user", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenDifferentIdAndUserRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 2;
        UserRequest request = createUserRequest();
        String expectedResponse =
                "{\"errorMessage\": \"User with id = '1' cannot get or change data of user with id = '2'\"}";
        assertEquals("user", userService.findById(id).getUsername());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("user", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenDifferentIdAndUserRequestAndRoleUser_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        String expectedResponse =
                "{\"errorMessage\": \"User with id = '2' cannot get or change data of user with id = '1'\"}";
        assertEquals("admin", userService.findById(id).getUsername());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("admin", userService.findById(id).getUsername());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 409")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndExistingUserRequestUsernameAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        UserRequest request = createUserRequest();
        request.setUsername("admin");
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_username_already_exists_response.json");
        assertEquals("shanfrey0@google.ru", userService.findById(id).getEmail());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isConflict())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("shanfrey0@google.ru", userService.findById(id).getEmail());
    }

    @Test
    @DisplayName("Test deleteById() role USER status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleUser_whenDeleteById_thenVoid() throws Exception {
        Integer id = 2;
        assertTrue(userRepository.existsById(id));

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role ADMIN status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleAdmin_whenDeleteById_thenVoid() throws Exception {
        Integer id = 1;
        assertTrue(userRepository.existsById(id));

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndRoleAnonymous_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 1;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertTrue(userRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                                    .andExpect(status().isUnauthorized())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(userRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenDifferentIdAndRoleUser_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 1;
        String expectedResponse =
                "{\"errorMessage\": \"User with id = '2' cannot get or change data of user with id = '1'\"}";
        assertTrue(userRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                                    .andExpect(status().isForbidden())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(userRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role ADMIN status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenDifferentIdAndRoleAdmin_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 2;
        String expectedResponse =
                "{\"errorMessage\": \"User with id = '1' cannot get or change data of user with id = '2'\"}";
        assertTrue(userRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                                    .andExpect(status().isForbidden())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(userRepository.existsById(id));
    }

    private UserRequest createUserRequest() {
        return UserRequest.builder()
                .username("new_user")
                .email("email@email.com")
                .password("password")
                .role("USER")
                .build();
    }
}
