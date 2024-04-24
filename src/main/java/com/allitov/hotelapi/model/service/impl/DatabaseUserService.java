package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.model.repository.UserRepository;
import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.model.service.util.ServiceUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {

    private final UserRepository userRepository;

    /**
     * Returns a user entity found by the specified username.
     * @param username a username by which to find the user entity.
     * @return a user entity found by username.
     * @throws EntityNotFoundException if the user with the specified username was not found.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format(ExceptionMessage.USER_BY_USERNAME_NOT_FOUND, username))
        );
    }

    /**
     * Returns a list of found users.
     * @return a list of found users.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Returns a user found by ID.
     * @param id an ID by which to find the user.
     * @return a user found by ID.
     * @throws EntityNotFoundException if the user with the specified ID was not found.
     */
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format(ExceptionMessage.USER_BY_ID_NOT_FOUND, id))
        );
    }

    /**
     * Creates a user from the specified user and returns it.
     * @param user a user to save.
     * @return a created user.
     * @throws EntityExistsException if the user with specified username and email already exists.
     */
    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException(MessageFormat.format(
                    ExceptionMessage.USER_ALREADY_EXISTS, user.getUsername()));
        }

        return userRepository.save(user);
    }

    /**
     * Updates a user found by the specified ID and returns it.
     * @param id an ID by which to update the user.
     * @param user a user to take data from.
     * @return an updated user.
     * @throws EntityNotFoundException if the user with the specified ID was not found.
     * @throws EntityExistsException if the user with the specified username already exists.
     */
    @Override
    public User updateById(Integer id, User user) {
        User foundUser = findById(id);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException(
                    MessageFormat.format(ExceptionMessage.USER_ALREADY_EXISTS, user.getUsername()));
        }
        ServiceUtils.copyNonNullProperties(user, foundUser);

        return userRepository.save(foundUser);
    }

    /**
     * Deletes a user by the specified ID.
     * @param id an ID by which to delete the user.
     */
    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
