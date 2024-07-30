package com.allitov.hotelapi.integration;

import com.allitov.testutils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StatisticsControllerIntegrationTest extends AbstractIntegrationTest {

    private final String baseUri = "/api/v1/statistics";

    @Test
    @DisplayName("Test getStatistics() status 200")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            value = "admin",
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleAdmin_whenGetStatistics_thenResource() throws Exception {
        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }

    @Test
    @DisplayName("Test getStatistics() status 401")
    @WithAnonymousUser
    public void givenRoleAnonymous_whenGetStatistics_thenErrorResponse() throws Exception {
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
    @DisplayName("Test getStatistics() status 403")
    @WithUserDetails(
            userDetailsServiceBeanName = USER_DETAILS_SERVICE_BEAN_NAME,
            setupBefore = TestExecutionEvent.TEST_METHOD
    )
    public void givenRoleUser_whenGetStatistics_thenErrorResponse() throws Exception {
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
}
