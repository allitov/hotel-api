package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.model.entity.Booking;
import com.allitov.hotelapi.model.entity.Room;
import com.allitov.hotelapi.model.entity.UnavailableDates;
import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.model.repository.BookingRepository;
import com.allitov.hotelapi.model.repository.UnavailableDatesRepository;
import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.model.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseBookingServiceTest {

    @InjectMocks
    private DatabaseBookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @Mock
    private UserService userService;

    @Mock
    private UnavailableDatesRepository unavailableDatesRepository;

    private Booking booking;

    private Room room;

    private User user;

    private UnavailableDates unavailableDates;

    @BeforeEach
    public void setUp() {
        room = Room.builder()
                .id(1)
                .description("description")
                .number((short) 101)
                .price(new BigDecimal("123.45"))
                .maxPeople((short) 5)
                .unavailableDates(List.of())
                .build();

        user = User.builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .password("password")
                .role(User.RoleType.USER)
                .build();

        booking = Booking.builder()
                .id(1)
                .room(room)
                .user(user)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();

        unavailableDates = UnavailableDates.builder()
                .id(1)
                .room(room)
                .from(LocalDate.of(2023, 12, 31))
                .to(LocalDate.of(2024, 1, 31))
                .build();
    }

    @Test
    @DisplayName("Test findAll()")
    public void givenVoid_whenFindAll_thenListOfBookings() {
        Mockito.when(bookingRepository.findAll())
                .thenReturn(List.of(booking));

        List<Booking> foundBookings = bookingService.findAll();

        assertThat(foundBookings).isNotNull();
        assertThat(foundBookings).hasSize(1);
        Mockito.verify(bookingRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("Test create()")
    public void givenBooking_whenCreate_thenBooking() {
        Mockito.when(userService.findById(1))
                .thenReturn(user);
        Mockito.when(roomService.findById(1))
                .thenReturn(room);
        Mockito.when(bookingRepository.save(booking))
                .thenReturn(booking);
        Mockito.when(unavailableDatesRepository.save(unavailableDates))
                .thenReturn(unavailableDates);

        Booking createdBooking = bookingService.create(booking);

        assertEquals(booking, createdBooking);
        Mockito.verify(userService, Mockito.times(1))
                .findById(1);
        Mockito.verify(roomService, Mockito.times(1))
                .findById(1);
        Mockito.verify(bookingRepository, Mockito.times(1))
                .save(booking);
        Mockito.verify(unavailableDatesRepository, Mockito.times(1))
                .save(unavailableDates);
    }

    @Test
    @DisplayName("Test create() with invalid dates")
    public void givenInvalidBookingDates_whenCreate_thenError() {
        booking.setFrom(LocalDate.of(2023, 12, 31));
        booking.setTo(LocalDate.of(2020, 1, 31));

        assertThrows(DateTimeException.class, () -> bookingService.create(booking));

        Mockito.verify(bookingRepository, Mockito.times(0))
                .save(booking);
        Mockito.verify(userService, Mockito.times(0))
                .findById(1);
        Mockito.verify(roomService, Mockito.times(0))
                .findById(1);
        Mockito.verify(unavailableDatesRepository, Mockito.times(0))
                .save(unavailableDates);
    }

    @Test
    @DisplayName("Test create() with nonexistent room")
    public void givenNonexistentBookingRoom_whenCreate_thenError() {
        Mockito.when(roomService.findById(1))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> bookingService.create(booking));

        Mockito.verify(roomService, Mockito.times(1))
                .findById(1);
        Mockito.verify(userService, Mockito.times(0))
                .findById(1);
        Mockito.verify(unavailableDatesRepository, Mockito.times(0))
                .save(unavailableDates);
        Mockito.verify(bookingRepository, Mockito.times(0))
                .save(booking);
    }

    @Test
    @DisplayName("Test create() with nonexistent user")
    public void givenNonexistentBookingUser_whenCreate_thenError() {
        Mockito.when(roomService.findById(1))
                .thenReturn(room);
        Mockito.when(userService.findById(1))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> bookingService.create(booking));

        Mockito.verify(roomService, Mockito.times(1))
                .findById(1);
        Mockito.verify(userService, Mockito.times(1))
                .findById(1);
        Mockito.verify(unavailableDatesRepository, Mockito.times(0))
                .save(unavailableDates);
        Mockito.verify(bookingRepository, Mockito.times(0))
                .save(booking);
    }

    @ParameterizedTest
    @MethodSource("createUnavailableDates")
    @DisplayName("Test create() with unavailable dates")
    public void givenUnavailableBookingDates_whenCreate_thenError(LocalDate from, LocalDate to) {
        UnavailableDates unavailableDates = UnavailableDates.builder().from(from).to(to).build();
        room.setUnavailableDates(List.of(unavailableDates));
        Mockito.when(roomService.findById(1))
                .thenReturn(room);
        Mockito.when(userService.findById(1))
                .thenReturn(user);

        assertThrows(DateTimeException.class, () -> bookingService.create(booking));

        Mockito.verify(roomService, Mockito.times(1))
                .findById(1);
        Mockito.verify(userService, Mockito.times(1))
                .findById(1);
        Mockito.verify(unavailableDatesRepository, Mockito.times(0))
                .save(unavailableDates);
        Mockito.verify(bookingRepository, Mockito.times(0))
                .save(booking);
    }

    private static Stream<Arguments> createUnavailableDates() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 31), LocalDate.of(2024, 1, 31)),
                Arguments.of(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 30)),
                Arguments.of(LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15)),
                Arguments.of(LocalDate.of(2024, 1, 10), LocalDate.of(2024, 3, 1)),
                Arguments.of(LocalDate.of(2023, 11, 10), LocalDate.of(2024, 1, 1))
        );
    }
}
