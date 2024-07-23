package com.allitov.hotelapi.integration;

import com.allitov.hotelapi.model.repository.RoomRepository;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.testutils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoomControllerIntegrationTest extends AbstractIntegrationTest {

    private final String baseUri = "/api/v1/room";

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Test
    @DisplayName("Test findAll() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleAdmin_whenFindAll_thenRoomListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_list_response.json");

        String actualResponse = mockMvc.perform(get(baseUri))
                                    .andExpect(status().isOk())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findAll() role USER status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleUser_whenFindAll_thenRoomListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_list_response.json");

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
    @DisplayName("Test findAll() role INVALID status 403")
    @WithMockUser(username = "user", authorities = "INVALID")
    public void givenRoleInvalid_whenFindAll_thenErrorResponse() throws Exception {
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
    @DisplayName("Test filterBy() role USER status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoomFilterAndRoleUser_whenFilterBy_thenRoomListWithCounterResponse() throws Exception {
        String description = "Quisque porta volutpat erat.";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_list_with_counter_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?description={desc}", description))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoomFilterAndRoleAdmin_whenFilterBy_thenRoomListWithCounterResponse() throws Exception {
        String description = "Quisque porta volutpat erat.";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_list_with_counter_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?description={desc}", description))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNullRoomFilterPageNumberAndRoleAdmin_whenFilterBy_thenErrorResponse() throws Exception {
        Integer pageSize = 10;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/filter_invalid_pagination_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?pageSize={pageSize}", pageSize))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 401")
    @WithAnonymousUser
    public void givenRoomFilterAndRoleAnonymous_whenFilterBy_thenErrorResponse() throws Exception {
        String description = "Quisque porta volutpat erat.";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?description={desc}", description))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 403")
    @WithMockUser(username = "user", authorities = "INVALID")
    public void givenRoomFilterAndRoleInvalid_whenFilterBy_thenErrorResponse() throws Exception {
        String description = "Quisque porta volutpat erat.";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?description={desc}", description))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findById() role USER status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleUser_whenFindById_thenRoomResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isOk())
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
    public void givenIdAndRoleAdmin_whenFindById_thenRoomResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_response.json");

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
        Integer id = 3;
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
    @DisplayName("Test findById() role INVALID status 403")
    @WithMockUser(username = "user", authorities = "INVALID")
    public void givenIdAndRoleInvalid_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 3;
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
                "response/room/room_by_id_not_found_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/{id}", id))
                                    .andExpect(status().isNotFound())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test create() role ADMIN status 201")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoomRequestAndRoleAdmin_whenCreate_thenVoid() throws Exception {
        RoomRequest request = createRoomRequest();
        assertEquals(5, roomRepository.count());

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/6"));

        assertEquals(6, roomRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenInvalidRoomRequestAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setDescription(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_request_blank_description_response.json");
        assertEquals(5, roomRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, roomRepository.count());
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenRoomRequestAndRoleAnonymous_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals(5, roomRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, roomRepository.count());
    }

    @Test
    @DisplayName("Test create() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoomRequestAndRoleUser_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertEquals(5, roomRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, roomRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentRoomRequestHotelIdAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setHotelId(1000);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_by_id_not_found_response.json");
        assertEquals(5, roomRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, roomRepository.count());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoomRequestAndRoleAdmin_whenUpdateById_thenVoid() throws Exception {
        Integer id = 3;
        RoomRequest request = createRoomRequest();
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        assertEquals("Cool description", roomService.findById(id).getDescription());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndInvalidRoomRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        RoomRequest request = createRoomRequest();
        request.setDescription(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_request_blank_description_response.json");
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());
    }

    @Test
    @DisplayName("Test updateById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndRoomRequestAndRoleAnonymous_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        RoomRequest request = createRoomRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());
    }

    @Test
    @DisplayName("Test updateById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoomRequestAndRoleUser_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        RoomRequest request = createRoomRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("Quisque porta volutpat erat.", roomService.findById(id).getDescription());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentIdAndRoomRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1000;
        RoomRequest request = createRoomRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_by_id_not_found_response.json");

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isNotFound())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndNonexistentRoomRequestHotelIdAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        RoomRequest request = createRoomRequest();
        request.setHotelId(1000);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_by_id_not_found_response.json");

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isNotFound())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test deleteById() role ADMIN status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleAdmin_whenDeleteById_thenVoid() throws Exception {
        Integer id = 3;
        assertTrue(roomRepository.existsById(id));

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        assertFalse(roomRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndRoleAnonymous_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertTrue(roomRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                                    .andExpect(status().isUnauthorized())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(roomRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleUser_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertTrue(roomRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                                    .andExpect(status().isForbidden())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(roomRepository.existsById(id));
    }

    private RoomRequest createRoomRequest() {
        return RoomRequest.builder()
                .hotelId(1)
                .description("Cool description")
                .number((short) 10)
                .price("100.15")
                .maxPeople((short) 5)
                .build();
    }
}
