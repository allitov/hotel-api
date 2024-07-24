package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.service.BookingService;
import com.allitov.hotelapi.web.dto.filter.BookingFilter;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.BookingListResponse;
import com.allitov.hotelapi.web.dto.response.BookingListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.BookingResponse;
import com.allitov.hotelapi.web.mapping.BookingMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = BookingController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class BookingControllerTest {

    private final String baseUri = "/api/v1/booking";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private BookingMapper bookingMapper;

    // methods tests

    @Test
    @DisplayName("Test findAll() status 200")
    public void givenVoid_whenFindAll_thenBookingListResponse() throws Exception {
        List<Booking> foundBookings = Collections.emptyList();
        Mockito.when(bookingService.findAll())
                .thenReturn(foundBookings);
        Mockito.when(bookingMapper.entityListToListResponse(foundBookings))
                .thenReturn(new BookingListResponse());

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'bookings': []}"));

        Mockito.verify(bookingService, Mockito.times(1))
                .findAll();
        Mockito.verify(bookingMapper, Mockito.times(1))
                .entityListToListResponse(foundBookings);
    }

    @Test
    @DisplayName("Test create() status 201")
    public void givenBookingRequest_whenCreate_thenBookingResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        BookingResponse response = createBookingResponse();
        Booking booking = new Booking();
        Mockito.when(bookingMapper.requestToEntity(request))
                .thenReturn(booking);
        Mockito.when(bookingService.create(booking))
                .thenReturn(booking);
        Mockito.when(bookingMapper.entityToResponse(booking))
                .thenReturn(response);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1, " +
                        "'roomId': 1, " +
                        "'userId': 1, " +
                        "'from': '2024-01-01', " +
                        "'to': '2024-01-31'}"));

        Mockito.verify(bookingMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(bookingService, Mockito.times(1))
                .create(booking);
        Mockito.verify(bookingMapper, Mockito.times(1))
                .entityToResponse(booking);
    }

    @Test
    @DisplayName("Test create() status 400")
    public void givenInvalidBookingRequest_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        Booking booking = new Booking();
        Mockito.when(bookingMapper.requestToEntity(request))
                .thenReturn(booking);
        Mockito.when(bookingService.create(booking))
                .thenThrow(new DateTimeException("Invalid dates."));

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Invalid dates.'}"));

        Mockito.verify(bookingMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(bookingService, Mockito.times(1))
                .create(booking);
    }

    @Test
    @DisplayName("Test create() status 404")
    public void givenNonexistentBookingRequestRoomId_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        Booking booking = new Booking();
        Mockito.when(bookingMapper.requestToEntity(request))
                .thenReturn(booking);
        Mockito.when(bookingService.create(booking))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(bookingMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(bookingService, Mockito.times(1))
                .create(booking);
    }

    @Test
    @DisplayName("Test filterBy() status 200")
    public void givenBookingFilter_whenFilterBy_thenBookingListWithCounterResponse() throws Exception {
        BookingFilter filter = new BookingFilter(1, 0);
        List<Booking> foundBookings = Collections.emptyList();
        Mockito.when(bookingService.filterBy(filter))
                .thenReturn(foundBookings);
        Mockito.when(bookingMapper.entityListToListWithCounterResponse(foundBookings))
                .thenReturn(new BookingListWithCounterResponse(0, Collections.emptyList()));

        mockMvc.perform(get(
                baseUri + "/filter?pageSize={size}&pageNumber={number}",
                        filter.getPageSize(), filter.getPageNumber()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'bookings': [], 'count': 0}"));

        Mockito.verify(bookingService, Mockito.times(1))
                .filterBy(filter);
        Mockito.verify(bookingMapper, Mockito.times(1))
                .entityListToListWithCounterResponse(foundBookings);
    }

    // validation tests

    @Test
    @DisplayName("Test BookingRequest validation with null room id")
    public void givenNullBookingRequestRoomId_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setRoomId(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Room id must be specified.'}"));
    }

    @Test
    @DisplayName("Test BookingRequest validation with null user id")
    public void givenNullBookingRequestUserId_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setUserId(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'User id must be specified.'}"));
    }

    @Test
    @DisplayName("Test BookingRequest validation with null 'from' date")
    public void givenNullBookingRequestFromDate_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setFrom(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': \"'from' date must be specified.\"}"));
    }

    @Test
    @DisplayName("Test BookingRequest validation with null 'to' date")
    public void givenNullBookingRequestToDate_whenCreate_thenErrorResponse() throws Exception {
        BookingRequest request = createBookingRequest();
        request.setTo(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': \"'to' date must be specified.\"}"));
    }

    private BookingRequest createBookingRequest() {
        return BookingRequest.builder()
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2024, 1, 1))
                .to(LocalDate.of(2024, 1, 31))
                .build();
    }

    private BookingResponse createBookingResponse() {
        return BookingResponse.builder()
                .id(1)
                .roomId(1)
                .userId(1)
                .from(LocalDate.of(2024, 1, 1))
                .to(LocalDate.of(2024, 1, 31))
                .build();
    }
}
