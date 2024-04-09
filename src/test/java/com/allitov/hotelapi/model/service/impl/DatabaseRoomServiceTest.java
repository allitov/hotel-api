package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseRoomServiceTest {

    @InjectMocks
    private DatabaseRoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    public void setUp() {
        room = Room.builder()
                .id(1)
                .description("description")
                .number((short) 101)
                .price(new BigDecimal("150.00"))
                .maxPeople((short) 2)
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void givenVoid_whenFindAll_thenListOfRooms() {
        Mockito.when(roomRepository.findAll())
                .thenReturn(List.of(room));

        List<Room> foundRooms = roomService.findAll();

        assertThat(foundRooms).isNotNull();
        assertThat(foundRooms).hasSize(1);
        Mockito.verify(roomRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("Test findById()")
    public void givenId_whenFindById_thenRoom() {
        Integer id = 1;
        Mockito.when(roomRepository.findById(id))
                .thenReturn(Optional.of(room));

        Room foundRoom = roomService.findById(id);

        assertEquals(room, foundRoom);
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test findById() EntityNotFoundException")
    public void givenNonexistentId_whenFindById_thenException() {
        Integer id = 10;
        Mockito.when(roomRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomService.findById(id));

        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test create()")
    public void givenRoom_whenCreate_thenRoom() {
        Mockito.when(roomRepository.save(room))
                .thenReturn(room);

        Room createdRoom = roomService.create(room);

        assertEquals(room, createdRoom);
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(room);
    }

    @Test
    @DisplayName("Test updateById()")
    public void givenIdAndRoom_whenUpdateById_thenRoom() {
        Integer id = 1;
        Mockito.when(roomRepository.findById(id))
                .thenReturn(Optional.of(room));
        Mockito.when(roomRepository.save(room))
                .thenReturn(room);

        Room updatedRoom = roomService.updateById(id, room);

        assertEquals(room, updatedRoom);
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(room);
    }

    @Test
    @DisplayName("Test updateById() EntityNotFoundException")
    public void givenNonexistentIdAndRoom_whenUpdateById_thenException() {
        Integer id = 10;
        Mockito.when(roomRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roomService.updateById(id, room));

        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(roomRepository, Mockito.times(0))
                .save(room);
    }

    @Test
    @DisplayName("Test deleteById()")
    public void givenVoid_whenDeleteById_thenVoid() {
        Integer id = 1;

        roomService.deleteById(id);

        Mockito.verify(roomRepository, Mockito.times(1))
                .deleteById(id);
    }
}
