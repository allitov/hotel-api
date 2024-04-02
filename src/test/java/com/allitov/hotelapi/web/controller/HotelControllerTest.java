package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import com.allitov.hotelapi.web.mapping.HotelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private HotelMapper hotelMapper;

    @Test
    @DisplayName("Test findAll() status 200")
    public void givenVoid_whenFindAll_thenHotelListResponse() throws Exception {
        List<Hotel> foundHotels = Collections.emptyList();
        Mockito.when(hotelService.findAll())
                .thenReturn(foundHotels);
        Mockito.when(hotelMapper.entityListToListResponse(foundHotels))
                .thenReturn(new HotelListResponse());

        mockMvc.perform(get("/api/v1/hotel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'hotels': []}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .findAll();
        Mockito.verify(hotelMapper, Mockito.times(1))
                .entityListToListResponse(foundHotels);
    }

    @Test
    @DisplayName("Test findById() status 200")
    public void givenId_whenFindById_thenHotelResponse() throws Exception {
        Integer id = 1;
        Hotel foundHotel = new Hotel();
        HotelResponse response = creteHotelResponse();
        Mockito.when(hotelService.findById(id))
                .thenReturn(foundHotel);
        Mockito.when(hotelMapper.entityToResponse(foundHotel))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/hotel/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1, " +
                        "'name': 'name', " +
                        "'description': 'description', " +
                        "'city': 'city', " +
                        "'address': 'address', " +
                        "'distanceFromCenter': 1.5, " +
                        "'rating': 4.5, " +
                        "'numberOfRatings': 200}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .findById(id);
        Mockito.verify(hotelMapper, Mockito.times(1))
                .entityToResponse(foundHotel);
    }

    @Test
    @DisplayName("Test create() status 201")
    public void givenHotelRequest_whenCreate_thenVoid() throws Exception {
        HotelRequest request = createHotelRequest();
        Integer id = 1;
        Hotel hotelFromRequest = new Hotel();
        hotelFromRequest.setId(id);
        Mockito.when(hotelMapper.requestToEntity(request))
                .thenReturn(hotelFromRequest);
        Mockito.when(hotelService.create(hotelFromRequest))
                .thenReturn(hotelFromRequest);

        mockMvc.perform(post("/api/v1/hotel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/hotel/" + id));

        Mockito.verify(hotelMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(hotelService, Mockito.times(1))
                .create(hotelFromRequest);
    }

    @Test
    @DisplayName("Test updateById() status 204")
    public void givenIdAndHotelRequest_whenUpdateById_thenVoid() throws Exception {
        Integer id = 1;
        HotelRequest request = createHotelRequest();
        Hotel hotel = new Hotel();
        Mockito.when(hotelMapper.requestToEntity(request))
                .thenReturn(hotel);
        Mockito.when(hotelService.updateById(id, hotel))
                .thenReturn(hotel);

        mockMvc.perform(put("/api/v1/hotel/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(hotelMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(hotelService, Mockito.times(1))
                .updateById(id, hotel);
    }

    @Test
    @DisplayName("Test deleteById() status 204")
    public void givenId_whenDeleteById_thenVoid() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete("/api/v1/hotel/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(hotelService, Mockito.times(1))
                .deleteById(id);
    }

    private HotelResponse creteHotelResponse() {
        return HotelResponse.builder()
                .id(1)
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .rating(4.5F)
                .numberOfRatings(200)
                .build();
    }

    private HotelRequest createHotelRequest() {
        return HotelRequest.builder()
                .name("name")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(1.5F)
                .build();
    }
}
