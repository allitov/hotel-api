package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.model.entity.Hotel;
import com.allitov.hotelapi.model.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseHotelServiceTest {

    @InjectMocks
    private DatabaseHotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    private Hotel hotel;

    @BeforeEach
    public void setup() {
        hotel = Hotel.builder()
                .id(1)
                .name("hotel")
                .description("description")
                .city("city")
                .address("address")
                .distanceFromCenter(4.5F)
                .rating(4.9F)
                .numberOfRatings(10)
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void givenVoid_whenFindAll_thenListOfHotel() {
        Mockito.when(hotelRepository.findAll())
                .thenReturn(List.of(hotel));

        List<Hotel> foundHotels = hotelService.findAll();

        assertThat(foundHotels).isNotNull();
        assertThat(foundHotels).hasSize(1);
        Mockito.verify(hotelRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("Test findById()")
    public void givenId_whenFindById_thenHotel() {
        Integer id = 1;
        Mockito.when(hotelRepository.findById(id))
                .thenReturn(Optional.of(hotel));

        Hotel foundHotel = hotelService.findById(id);

        assertEquals(hotel, foundHotel);
        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test findById() EntityNotFoundException")
    public void givenNonexistentId_whenFindById_thenException() {
        Integer id = 10;
        Mockito.when(hotelRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hotelService.findById(id));

        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test create()")
    public void givenHotel_whenCreate_thenHotel() {
        Mockito.when(hotelRepository.save(hotel))
                .thenReturn(hotel);

        Hotel createdHotel = hotelService.create(hotel);

        assertEquals(0F, createdHotel.getRating());
        assertEquals(0, createdHotel.getNumberOfRatings());
        Mockito.verify(hotelRepository, Mockito.times(1))
                .save(hotel);
    }

    @Test
    @DisplayName("Test updateById()")
    public void givenIdAndHotel_whenUpdateById_thenHotel() {
        Integer id = 1;
        Mockito.when(hotelRepository.findById(id))
                .thenReturn(Optional.of(hotel));
        Mockito.when(hotelRepository.save(hotel))
                .thenReturn(hotel);

        Hotel updatedHotel = hotelService.updateById(id, hotel);

        assertEquals(hotel, updatedHotel);
        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(hotelRepository, Mockito.times(1))
                .save(hotel);
    }

    @Test
    @DisplayName("Test updateById() EntityNotFoundException")
    public void givenNonexistentIdAndHotel_whenUpdateById_thenException() {
        Integer id = 10;
        Mockito.when(hotelRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> hotelService.updateById(id, hotel));

        Mockito.verify(hotelRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(hotelRepository, Mockito.times(0))
                .save(hotel);
    }

    @Test
    @DisplayName("Test deleteById()")
    public void givenVoid_whenDeleteById_thenVoid() {
        Integer id = 1;

        hotelService.deleteById(id);

        Mockito.verify(hotelRepository, Mockito.times(1))
                .deleteById(id);
    }
}
