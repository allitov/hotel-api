package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatisticsController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class StatisticsControllerTest {

    private final String baseUri = "/api/v1/statistics";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService<?> statisticsService;

    // methods tests

    @Test
    @DisplayName("Test getStatistics() status 200")
    public void givenVoid_whenGetStatistics_thenResource() throws Exception {
        byte[] data = new byte[]{1};
        Mockito.when(statisticsService.getData()).thenReturn(data);

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(data));
    }
}
