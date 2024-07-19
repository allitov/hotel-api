package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.filter.HotelFilter;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import com.allitov.hotelapi.web.mapping.HotelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.utility.RandomString;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HotelController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class HotelControllerTest {

    private final String baseUri = "/api/v1/hotel";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private HotelMapper hotelMapper;

    // methods tests

    @Test
    @DisplayName("Test findAll() status 200")
    public void givenVoid_whenFindAll_thenHotelListResponse() throws Exception {
        List<Hotel> foundHotels = Collections.emptyList();
        Mockito.when(hotelService.findAll())
                .thenReturn(foundHotels);
        Mockito.when(hotelMapper.entityListToListResponse(foundHotels))
                .thenReturn(new HotelListResponse());

        mockMvc.perform(get(baseUri))
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

        mockMvc.perform(get(baseUri + "/{id}", id))
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
    @DisplayName("Test findById() status 404")
    public void givenNonexistentId_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 10;
        Mockito.when(hotelService.findById(id))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(get(baseUri + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .findById(id);
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

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/" + id));

        Mockito.verify(hotelMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(hotelService, Mockito.times(1))
                .create(hotelFromRequest);
    }

    @Test
    @DisplayName("Test create() status 400")
    public void givenInvalidHotelRequest_whenCreate_thenErrorResponse() throws Exception {
        HotelRequest request = createHotelRequest();
        request.setName(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Name must be specified.'}"));
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

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(hotelMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(hotelService, Mockito.times(1))
                .updateById(id, hotel);
    }

    @Test
    @DisplayName("Test updateById() status 400")
    public void givenIdAndInvalidHotelRequest_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        HotelRequest request = createHotelRequest();
        request.setName(null);

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Name must be specified.'}"));
    }

    @Test
    @DisplayName("Test updateById() status 404")
    public void givenNonexistentIdAndHotelRequest_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 10;
        HotelRequest request = createHotelRequest();
        Hotel hotel = new Hotel();
        Mockito.when(hotelMapper.requestToEntity(request))
                .thenReturn(hotel);
        Mockito.when(hotelService.updateById(id, hotel))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(hotelMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(hotelService, Mockito.times(1))
                .updateById(id, hotel);
    }

    @Test
    @DisplayName("Test deleteById() status 204")
    public void givenId_whenDeleteById_thenVoid() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(hotelService, Mockito.times(1))
                .deleteById(id);
    }

    @Test
    @DisplayName("Test updateRatingById() status 200")
    public void givenIdAndRating_whenUpdateRatingById_thenHotel() throws Exception {
        Integer id = 1;
        Integer newMark = 5;
        Hotel hotel = new Hotel();
        HotelResponse response = creteHotelResponse();
        Mockito.when(hotelService.updateRatingById(id, newMark))
                .thenReturn(hotel);
        Mockito.when(hotelMapper.entityToResponse(hotel))
                .thenReturn(response);

        mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
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
                .updateRatingById(id, newMark);
        Mockito.verify(hotelMapper, Mockito.times(1))
                .entityToResponse(hotel);
    }

    @Test
    @DisplayName("Test updateRatingById() status 400")
    public void givenIdAndIllegalRating_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 1;
        Integer newMark = 10;
        Mockito.when(hotelService.updateRatingById(id, newMark))
                .thenThrow(new IllegalArgumentException("Illegal rating"));

        mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Illegal rating'}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .updateRatingById(id, newMark);
    }

    @Test
    @DisplayName("Test updateRatingById() status 404")
    public void givenNonexistentIdAndRating_whenUpdateRatingById_thenErrorResponse() throws Exception {
        Integer id = 10;
        Integer newMark = 5;
        Mockito.when(hotelService.updateRatingById(id, newMark))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(patch(baseUri + "/{id}?newMark={newMark}", id, newMark))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .updateRatingById(id, newMark);
    }

    @Test
    @DisplayName("Test filterBy() status 200")
    public void givenFilter_whenFilterBy_thenHotelListWithCounter() throws Exception {
        HotelFilter filter = new HotelFilter();
        filter.setName("name");
        List<Hotel> foundHotels = Collections.emptyList();
        Mockito.when(hotelService.filterBy(filter))
                .thenReturn(foundHotels);
        Mockito.when(hotelMapper.entityListToListWithCounterResponse(foundHotels))
                .thenReturn(new HotelListWithCounterResponse(Collections.emptyList(), 0));

        mockMvc.perform(get(baseUri + "/filter?name={name}", filter.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'hotels': [], 'count': 0}"));

        Mockito.verify(hotelService, Mockito.times(1))
                .filterBy(filter);
        Mockito.verify(hotelMapper, Mockito.times(1))
                .entityListToListWithCounterResponse(foundHotels);
    }

    // validation tests

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test HotelRequest validation with blank name")
    public void givenBlankHotelRequestName_whenCreate_thenErrorResponse(String name) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setName(name);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Name must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidStrings")
    @DisplayName("Test HotelRequest validation with invalid name")
    public void givenInvalidHotelRequestName_whenCreate_thenErrorResponse(String name) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setName(name);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Name length must be <= 50.'}"));
    }

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test HotelRequest validation with blank description")
    public void givenBlankHotelRequestDescription_whenCreate_thenErrorResponse(String description) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setDescription(description);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Description must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidStrings")
    @DisplayName("Test HotelRequest validation with invalid description")
    public void givenInvalidHotelRequestDescription_whenCreate_thenErrorResponse(String description) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setDescription(description);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Description length must be <= 255.'}"));
    }

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test HotelRequest validation with blank city")
    public void givenBlankHotelRequestCity_whenCreate_thenErrorResponse(String city) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setCity(city);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'City must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidStrings")
    @DisplayName("Test HotelRequest validation with invalid city")
    public void givenInvalidHotelRequestCity_whenCreate_thenErrorResponse(String city) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setCity(city);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'City length must be <= 255.'}"));
    }

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test HotelRequest validation with blank address")
    public void givenBlankHotelRequestAddress_whenCreate_thenErrorResponse(String address) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setAddress(address);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Address must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidStrings")
    @DisplayName("Test HotelRequest validation with invalid address")
    public void givenInvalidHotelRequestAddress_whenCreate_thenErrorResponse(String address) throws Exception {
        HotelRequest request = createHotelRequest();
        request.setAddress(address);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Address length must be <= 255.'}"));
    }

    @Test
    @DisplayName("Test HotelRequest validation with null distance from center")
    public void givenNullHotelRequestDistanceFromCenter_whenCreate_thenErrorResponse() throws Exception {
        HotelRequest request = createHotelRequest();
        request.setDistanceFromCenter(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Distance from center must be specified.'}"));
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

    private static Stream<Arguments> createBlankStrings() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("     "),
                Arguments.of(" \n\n\n  ")
        );
    }

    private static Stream<Arguments> createInvalidStrings() {
        return Stream.of(
                Arguments.of(RandomString.make(256))
        );
    }
}
