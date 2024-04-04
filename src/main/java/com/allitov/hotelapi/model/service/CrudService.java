package com.allitov.hotelapi.model.service;

import java.util.List;

/**
 * Provides CRUD methods to manipulate with an entity data.
 * @param <T> a type of entity.
 * @param <ID> a type of entity ID.
 * @author allitov
 */
public interface CrudService<T, ID> {

    /**
     * Returns a list of found entities.
     * @return a list of found entities.
     */
    List<T> findAll();

    /**
     * Returns an entity found by ID.
     * @param id an ID by which to find the entity.
     * @return an entity found by ID.
     * @throws jakarta.persistence.EntityNotFoundException if the entity with the specified ID was not found.
     */
    T findById(ID id);

    /**
     * Creates an entity from the specified object and returns it.
     * @param object an entity to save.
     * @return a created entity.
     */
    T create(T object);

    /**
     * Updates an entity found by the specified ID and returns it.
     * @param id an ID by which to update the entity.
     * @param object an entity to take data from.
     * @return an updated entity.
     * @throws jakarta.persistence.EntityNotFoundException if the entity with the specified ID was not found.
     */
    T updateById(ID id, T object);

    /**
     * Deletes an entity by the specified ID.
     * @param id an ID by which to delete the entity.
     */
    void deleteById(ID id);
}
