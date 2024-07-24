package com.allitov.hotelapi.integration;

import com.allitov.hotelapi.model.repository.BookingRepository;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.testutils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingControllerIntegrationTest extends AbstractIntegrationTest {

    private final String baseUri = "/api/v1/booking";

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @DisplayName("Test findAll() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleAdmin_whenFindAll_thenBookingListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/booking/booking_list_response.json");

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
    @DisplayName("Test filterBy() status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenBookingFilterAndRoleAdmin_whenFilterBy_thenBookingWithCounterListResponse() throws Exception {
        Integer pageSize = 1;
        Integer pageNumber = 0;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/booking/booking_list_with_counter_response.json");

        String actualResponse = mockMvc.perform(get(
                baseUri + "/filter?pageSize={size}&pageNumber={number}", pageSize, pageNumber))
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
    public void givenNullBookingFilterPageNumberAndRoleAdmin_whenFilterBy_thenErrorResponse() throws Exception {
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
    public void givenBookingFilterRoleAnonymous_whenFilterBy_thenErrorResponse() throws Exception {
        Integer pageSize = 1;
        Integer pageNumber = 0;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(get(
                        baseUri + "/filter?pageSize={size}&pageNumber={number}", pageSize, pageNumber))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenBookingFilterRoleUser_whenFilterBy_thenErrorResponse() throws Exception {
        Integer pageSize = 1;
        Integer pageNumber = 0;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(get(
                        baseUri + "/filter?pageSize={size}&pageNumber={number}", pageSize, pageNumber))
                .andExpect(status().isForbidden())
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
    public void givenBookingRequestAndRoleAdmin_whenCreate_thenBookingResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        assertEquals(5, bookingRepository.count());

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(6, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role USER status 201")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenBookingRequestAndRoleUser_whenCreate_thenBookingResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        assertEquals(5, bookingRepository.count());

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(6, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenInvalidBookingRequestAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setRoomId(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/booking/booking_request_blank_room_id.json");
        assertEquals(5, bookingRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenBookingRequestAndRoleAnonymous_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals(5, bookingRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role INVALID status 403")
    @WithMockUser(username = "user", authorities = "INVALID")
    public void givenBookingRequestAndRoleInvalid_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertEquals(5, bookingRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isForbidden())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentBookingRequestRoomIdAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setRoomId(1000);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/room/room_by_id_not_found_response.json");
        assertEquals(5, bookingRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isNotFound())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, bookingRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentBookingRequestUserIdAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setUserId(1000);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/user/user_by_id_not_found_response.json");
        assertEquals(5, bookingRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isNotFound())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, bookingRepository.count());
    }

    private BookingRequest createBookingRequest() {
        return BookingRequest.builder()
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2024, 1, 1))
                .to(LocalDate.of(2024, 1, 31))
                .build();
    }
}
