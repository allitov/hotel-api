package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.web.dto.filter.RoomFilter;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.RoomListResponse;
import com.allitov.hotelapi.web.dto.response.RoomListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import com.allitov.hotelapi.web.mapping.RoomMapper;
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

@WebMvcTest(value = RoomController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class RoomControllerTest {

    private final String baseUri = "/api/v1/room";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @MockBean
    private RoomMapper roomMapper;

    // methods tests

    @Test
    @DisplayName("Test findAll() status 200")
    public void givenVoid_whenFindAll_thenRoomListResponse() throws Exception {
        List<Room> foundRooms = List.of();
        Mockito.when(roomService.findAll())
                .thenReturn(foundRooms);
        Mockito.when(roomMapper.entityListToListResponse(foundRooms))
                .thenReturn(new RoomListResponse());

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'rooms': []}"));

        Mockito.verify(roomService, Mockito.times(1))
                .findAll();
        Mockito.verify(roomMapper, Mockito.times(1))
                .entityListToListResponse(foundRooms);
    }

    @Test
    @DisplayName("Test findById() status 200")
    public void givenId_whenFindById_thenRoomResponse() throws Exception {
        Integer id = 1;
        Room foundRoom = new Room();
        RoomResponse response = createRoomResponse();
        Mockito.when(roomService.findById(id))
                .thenReturn(foundRoom);
        Mockito.when(roomMapper.entityToResponse(foundRoom))
                .thenReturn(response);

        mockMvc.perform(get(baseUri + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1, " +
                        "'hotelId': 1, " +
                        "'description': 'description', " +
                        "'price': '123.45', " +
                        "'maxPeople': 2}"));

        Mockito.verify(roomService, Mockito.times(1))
                .findById(id);
        Mockito.verify(roomMapper, Mockito.times(1))
                .entityToResponse(foundRoom);
    }

    @Test
    @DisplayName("Test findById() status 404")
    public void givenNonexistentId_whenFindById_thenErrorResponse() throws Exception {
        Integer id = 10;
        Mockito.when(roomService.findById(id))
                .thenThrow(new EntityNotFoundException("Entity not found."));

        mockMvc.perform(get(baseUri + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Entity not found.'}"));

        Mockito.verify(roomService, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test create() status 201")
    public void givenRoomRequest_whenCreate_thenVoid() throws Exception {
        RoomRequest request = createRoomRequest();
        Integer id = 1;
        Room roomFromRequest = new Room();
        roomFromRequest.setId(id);
        Mockito.when(roomMapper.requestToEntity(request))
                .thenReturn(roomFromRequest);
        Mockito.when(roomService.create(roomFromRequest))
                .thenReturn(roomFromRequest);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", baseUri + "/" + id));

        Mockito.verify(roomMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(roomService, Mockito.times(1))
                .create(roomFromRequest);
    }

    @Test
    @DisplayName("Test create() status 400")
    public void givenInvalidRoomRequest_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setHotelId(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Hotel id must be specified.'}"));
    }

    @Test
    @DisplayName("Test create() status 404")
    public void givenNonexistentRoomRequestHotelId_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        Room roomFromRequest = new Room();
        Mockito.when(roomMapper.requestToEntity(request))
                .thenReturn(roomFromRequest);
        Mockito.when(roomService.create(roomFromRequest))
                .thenThrow(new EntityNotFoundException("Hotel not found."));

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Hotel not found.'}"));

        Mockito.verify(roomMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(roomService, Mockito.times(1))
                .create(roomFromRequest);
    }

    @Test
    @DisplayName("Test updateById() status 204")
    public void givenIdAndRoomRequest_whenUpdateById_thenVoid() throws Exception {
        Integer id = 1;
        RoomRequest request = createRoomRequest();
        Room roomFromRequest = new Room();
        Mockito.when(roomMapper.requestToEntity(request))
                .thenReturn(roomFromRequest);
        Mockito.when(roomService.updateById(id, roomFromRequest))
                .thenReturn(roomFromRequest);

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(roomMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(roomService, Mockito.times(1))
                .updateById(id, roomFromRequest);
    }

    @Test
    @DisplayName("Test updateById() status 400")
    public void givenIdAndInvalidRoomRequest_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 1;
        RoomRequest request = createRoomRequest();
        request.setHotelId(null);

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Hotel id must be specified.'}"));
    }

    @Test
    @DisplayName("Test updateById() status 404")
    public void givenNonexistentIdAndRoomRequest_whenUpdateById_thenErrorResponse() throws Exception {
        Integer id = 10;
        RoomRequest request = createRoomRequest();
        Room roomFromRequest = new Room();
        Mockito.when(roomMapper.requestToEntity(request))
                .thenReturn(roomFromRequest);
        Mockito.when(roomService.updateById(id, roomFromRequest))
                .thenThrow(new EntityNotFoundException("Room not found."));

        mockMvc.perform(put(baseUri + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Room not found.'}"));

        Mockito.verify(roomMapper, Mockito.times(1))
                .requestToEntity(request);
        Mockito.verify(roomService, Mockito.times(1))
                .updateById(id, roomFromRequest);
    }

    @Test
    @DisplayName("Test deleteById() status 204")
    public void givenId_whenDeleteById_thenVoid() throws Exception {
        Integer id = 1;

        mockMvc.perform(delete(baseUri + "/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(roomService, Mockito.times(1))
                .deleteById(id);
    }

    @Test
    @DisplayName("Test filterBy() status 200")
    public void givenRoomFilter_whenFilterBy_thenRoomWithCounterResponse() throws Exception {
        RoomFilter filter = new RoomFilter();
        filter.setDescription("description");
        List<Room> foundRooms = Collections.emptyList();
        Mockito.when(roomService.filterBy(filter))
                .thenReturn(foundRooms);
        Mockito.when(roomMapper.entityListToListWithCounterResponse(foundRooms))
                .thenReturn(new RoomListWithCounterResponse(0, Collections.emptyList()));

        mockMvc.perform(get(baseUri + "/filter?description={description}", filter.getDescription()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'rooms': [], 'count': 0}"));

        Mockito.verify(roomService, Mockito.times(1))
                .filterBy(filter);
        Mockito.verify(roomMapper, Mockito.times(1))
                .entityListToListWithCounterResponse(foundRooms);
    }

    // validation tests

    @Test
    @DisplayName("Test RoomRequest validation with null hotel id")
    public void givenNullRoomRequestHotelId_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setHotelId(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Hotel id must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createBlankStrings")
    @DisplayName("Test RoomRequest validation with blank description")
    public void givenBlankRoomRequestDescription_whenCreate_thenErrorResponse(String description) throws Exception {
        RoomRequest request = createRoomRequest();
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
    @DisplayName("Test RoomRequest validation with invalid description")
    public void givenInvalidRoomRequestDescription_whenCreate_thenErrorResponse(String description) throws Exception {
        RoomRequest request = createRoomRequest();
        request.setDescription(description);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Description length must be <= 255.'}"));
    }

    @Test
    @DisplayName("Test RoomRequest validation with null number")
    public void givenNullRoomRequestNumber_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setNumber(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Number must be specified.'}"));
    }

    @Test
    @DisplayName("Test RoomRequest validation with null price")
    public void givenNullRoomRequestPrice_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setPrice(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'errorMessage': 'Price must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createInvalidStrings")
    @DisplayName("Test RoomRequest validation with invalid price")
    public void givenInvalidRoomRequestPrice_whenCreate_thenErrorResponse(String price) throws Exception {
        RoomRequest request = createRoomRequest();
        request.setPrice(price);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test RoomRequest validation with null max people")
    public void givenNullRoomRequestMaxPeople_whenCreate_thenErrorResponse() throws Exception {
        RoomRequest request = createRoomRequest();
        request.setMaxPeople(null);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{'errorMessage': 'Maximum number of people must be specified.'}"));
    }

    @ParameterizedTest
    @MethodSource("createNotPositiveNumbers")
    @DisplayName("Test RoomRequest validation with invalid max people")
    public void givenInvalidRoomRequestMaxPeople_whenCreate_thenErrorResponse(int maxPeople) throws Exception {
        RoomRequest request = createRoomRequest();
        request.setMaxPeople((short) maxPeople);

        mockMvc.perform(post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{'errorMessage': 'Maximum number of people must be greater than zero.'}"));
    }

    private RoomResponse createRoomResponse() {
        return RoomResponse.builder()
                .id(1)
                .hotelId(1)
                .description("description")
                .number((short) 101)
                .price("123.45")
                .maxPeople((short) 2)
                .build();
    }

    private RoomRequest createRoomRequest() {
        return RoomRequest.builder()
                .hotelId(1)
                .description("description")
                .number((short) 101)
                .price("123.45")
                .maxPeople((short) 2)
                .build();
    }

    private static Stream<Arguments> createBlankStrings() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of("     "),
                Arguments.of(" \n\n\n")
        );
    }

    private static Stream<Arguments> createInvalidStrings() {
        return Stream.of(
                Arguments.of(RandomString.make(256))
        );
    }

    private static Stream<Arguments> createNotPositiveNumbers() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-100)
        );
    }
}
