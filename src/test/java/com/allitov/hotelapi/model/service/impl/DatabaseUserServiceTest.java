package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.model.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
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
public class DatabaseUserServiceTest {

    @InjectMocks
    private DatabaseUserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User
                .builder()
                .id(1)
                .username("username")
                .email("email@email.com")
                .password("password")
                .role(User.RoleType.USER)
                .build();
    }

    @Test
    @DisplayName("Test findByUsername()")
    public void givenUsername_whenFindByUsername_thenUser() {
        String username = "username";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername(username);

        assertEquals(user, foundUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(username);
    }

    @Test
    @DisplayName("Test findByUsername() EntityNotFoundException")
    public void givenNonexistentUsername_whenFindByUsername_thenException() {
        String username = "nonexistent_username";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(username));

        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(username);
    }

    @Test
    @DisplayName("Test findAll()")
    public void givenVoid_whenFindAll_thenListOfUsers() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<User> foundUsers = userService.findAll();

        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(1);
        Mockito.verify(userRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("Test findById()")
    public void givenId_whenFindById_thenUser() {
        Integer id = 1;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        User foundUser = userService.findById(id);

        assertEquals(user, foundUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test findById() EntityNotFoundException")
    public void givenNonexistentId_whenFindById_thenException() {
        Integer id = 1;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));

        Mockito.verify(userRepository, Mockito.times(1))
                .findById(id);
    }

    @Test
    @DisplayName("Test create()")
    public void givenUser_whenCreate_thenUser() {
        Mockito.when(userRepository.existsByUsernameAndEmail(user.getUsername(), user.getEmail()))
                .thenReturn(false);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        User createdUser = userService.create(user);

        assertEquals(user, createdUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .existsByUsernameAndEmail(user.getUsername(), user.getEmail());
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
    }

    @Test
    @DisplayName("Test create() EntityExistsException")
    public void givenExistingUser_whenCreate_thenException() {
        Mockito.when(userRepository.existsByUsernameAndEmail(user.getUsername(), user.getEmail()))
                .thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.create(user));

        Mockito.verify(userRepository, Mockito.times(1))
                .existsByUsernameAndEmail(user.getUsername(), user.getEmail());
        Mockito.verify(userRepository, Mockito.times(0))
                .save(user);
    }

    @Test
    @DisplayName("Test updateById()")
    public void givenIdAndUser_whenUpdateById_thenUser() {
        Integer id = 1;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        User updatedUser = userService.updateById(id, user);

        assertEquals(user, updatedUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
    }

    @Test
    @DisplayName("Test updateById() EntityNotFoundException")
    public void givenNonexistentIdAndUser_whenUpdateById_thenException() {
        Integer id = 1;
        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateById(id, user));

        Mockito.verify(userRepository, Mockito.times(1))
                .findById(id);
        Mockito.verify(userRepository, Mockito.times(0))
                .save(user);
    }

    @Test
    @DisplayName("Test deleteById()")
    public void givenId_whenDeleteById_thenVoid() {
        Integer id = 1;

        userService.deleteById(id);

        Mockito.verify(userRepository, Mockito.times(1))
                .deleteById(id);
    }
}
