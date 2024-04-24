package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Provides methods for working with a user entity table in a database.
 * @author allitov
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Returns an optional of user entity found by the specified username.
     * @param username a username by which to find the user entity.
     * @return an optional of user entity found by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user entity with the specified username exists.
     * @param username a username by which to find user entity.
     * @return <i>true</i> if user with the specified username exists, <i>false</i> otherwise.
     */
    boolean existsByUsername(String username);
}
