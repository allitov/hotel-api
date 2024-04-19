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
}
