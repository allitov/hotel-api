package com.allitov.hotelapi.integration;

import com.allitov.hotelapi.model.repository.HotelRepository;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
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

public class HotelControllerIntegrationTest extends AbstractIntegrationTest {

    private final String baseUri = "/api/v1/hotel";

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Test
    @DisplayName("Test findAll() role USER status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleUser_whenFindAll_thenHotelListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_list_response.json");

        String actualResponse = mockMvc.perform(get(baseUri))
                                    .andExpect(status().isOk())
                                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                    .andReturn()
                                    .getResponse()
                                    .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test findAll() role ADMIN status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleAdmin_whenFindAll_thenHotelListResponse() throws Exception {
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_list_response.json");

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
    @DisplayName("Test findById() role USER status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRoleUser_whenFindById_thenHotelResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_response.json");

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
    public void givenIdAndRoleAdmin_whenFindById_thenHotelResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_response.json");

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
                "response/hotel/hotel_by_id_not_found_response.json");

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
    public void givenHotelRequestAndRoleAdmin_whenCreate_thenVoid() throws Exception {
        HotelRequest request = createHotelRequest();
        assertEquals(5, hotelRepository.count());

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/6"));

        assertEquals(6, hotelRepository.count());
    }

    @Test
    @DisplayName("Test create() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenInvalidHotelRequestAndRoleAdmin_whenCreate_thenErrorResponse() throws Exception {
        HotelRequest request = createHotelRequest();
        request.setName(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_request_blank_name_response.json");
        assertEquals(5, hotelRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, hotelRepository.count());
    }

    @Test
    @DisplayName("Test create() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenHotelRequestAndRoleAnonymous_whenCreate_thenErrorResponse() throws Exception {
        HotelRequest request = createHotelRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals(5, hotelRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, hotelRepository.count());
    }

    @Test
    @DisplayName("Test create() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenHotelRequestAndRoleUser_whenCreate_thenErrorResponse() throws Exception {
        HotelRequest request = createHotelRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertEquals(5, hotelRepository.count());

        String actualResponse = mockMvc.perform(post(baseUri)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals(5, hotelRepository.count());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 204")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndHotelRequestAndRoleAdmin_whenUpdateById_thenVoid() throws Exception {
        Integer id = 3;
        HotelRequest request = createHotelRequest();
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        assertEquals("100 Street", hotelService.findById(id).getAddress());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndInvalidHotelRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        HotelRequest request = createHotelRequest();
        request.setName(null);
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_request_blank_name_response.json");
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());
    }

    @Test
    @DisplayName("Test updateById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndHotelRequestAndRoleAnonymous_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        HotelRequest request = createHotelRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());
    }

    @Test
    @DisplayName("Test updateById() role USER status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndHotelRequestAndRoleUser_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 3;
        HotelRequest request = createHotelRequest();
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());

        String actualResponse = mockMvc.perform(put(baseUri + "/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertEquals("6 Meadow Vale Plaza", hotelService.findById(id).getAddress());
    }

    @Test
    @DisplayName("Test updateById() role ADMIN status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentIdAndHotelRequestAndRoleAdmin_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1000;
        HotelRequest request = createHotelRequest();
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
        assertTrue(hotelRepository.existsById(id));

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        assertFalse(hotelRepository.existsById(id));
    }

    @Test
    @DisplayName("Test deleteById() role ANONYMOUS status 401")
    @WithAnonymousUser
    public void givenIdAndRoleAnonymous_whenDeleteById_thenErrorResponse() throws Exception {
        Integer id = 3;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");
        assertTrue(hotelRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(hotelRepository.existsById(id));
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
        assertTrue(hotelRepository.existsById(id));

        String actualResponse = mockMvc.perform(delete(baseUri + "/{id}", id))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
        assertTrue(hotelRepository.existsById(id));
    }

    @Test
    @DisplayName("Test updateRatingById() status 200 role USER")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRatingAndRoleUser_whenUpdateRatingById_thenHotel() throws Exception {
        Integer id = 3;
        Integer newMark = 5;

        mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test updateRatingById() status 200 role ADMIN")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndRatingAndRoleAdmin_whenUpdateRatingById_thenHotel() throws Exception {
        Integer id = 3;
        Integer newMark = 5;

        mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test updateRatingById() status 400")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenIdAndInvalidRatingAndRoleAdmin_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 3;
        Integer newMark = 10;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_illegal_rating_response.json");

        String actualResponse = mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                            .andExpect(status().isBadRequest())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test updateRatingById() status 401")
    @WithAnonymousUser
    public void givenIdAndRatingAndRoleAnonymous_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 3;
        Integer newMark = 5;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                            .andExpect(status().isUnauthorized())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test updateRatingById() status 403")
    @WithMockUser(username = "user", authorities = "INVALID")
    public void givenIdAndRatingAndRoleInvalid_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 3;
        Integer newMark = 5;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test updateRatingById() status 404")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenNonexistentIdAndRatingAndRoleAdmin_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 1000;
        Integer newMark = 5;
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_by_id_not_found_response.json");

        String actualResponse = mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                            .andExpect(status().isNotFound())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 200 role USER")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenHotelFilterAndRoleUser_whenFilterBy_thenHotelListWithCounterResponse() throws Exception {
        String hotelName = "Lubowitz LLC";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_list_with_counter_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?name={name}", hotelName))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test filterBy() status 200 role ADMIN")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenHotelFilterAndRoleAdmin_whenFilterBy_thenHotelListWithCounterResponse() throws Exception {
        String hotelName = "Lubowitz LLC";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/hotel/hotel_list_with_counter_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?name={name}", hotelName))
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
    public void givenNullHotelFilterPageNumberAndRoleAdmin_whenFilterBy_thenErrorResponse() throws Exception {
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
    public void givenHotelFilterAndRoleAnonymous_whenFilterBy_thenErrorResponse() throws Exception {
        String hotelName = "Lubowitz LLC";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/authentication_failure_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?name={name}", hotelName))
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
    public void givenHotelFilterAndRoleInvalid_whenFilterBy_thenErrorResponse() throws Exception {
        String hotelName = "Lubowitz LLC";
        String expectedResponse = TestUtils.readStringFromResource(
                "response/access_denied_response.json");

        String actualResponse = mockMvc.perform(get(baseUri + "/filter?name={name}", hotelName))
                            .andExpect(status().isForbidden())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertJsonEquals(expectedResponse, actualResponse);
    }

    private HotelRequest createHotelRequest() {
        return HotelRequest.builder()
                .name("Hotel Name")
                .description("The Best Description Ever!")
                .city("Cool City")
                .address("100 Street")
                .distanceFromCenter(1.5F)
                .build();
    }
}
