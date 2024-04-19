package com.allitov.hotelapi.model.service;

import com.allitov.hotelapi.model.entity.User;

/**
 * Provides methods to manipulate with a user entity data.
 * @author allitov
 */
public interface UserService extends CrudService<User, Integer> {

    /**
     * Returns a user entity found by the specified username.
     * @param username a username by which to find the user entity.
     * @return a user entity found by username.
     */
    User findByUsername(String username);

    /**
     * Checks if a user entity with specified username and email exists.
     * @param username a username by which to find user entity.
     * @param email an email by which to find user entity.
     * @return <i>true</i> if user with specified username and email exists, <i>false</i> otherwise.
     */
    boolean existsByUsernameAndEmail(String username, String email);
}
