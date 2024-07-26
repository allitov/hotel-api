package com.allitov.hotelapi.model.service;

import java.io.File;

/**
 * Provides methods to manipulate with the application statistics.
 * @param <T> a type to represent a statistics data.
 * @author allitov
 */
public interface StatisticsService<T> {

    /**
     * Persists statistics.
     * @param data a data to persist.
     */
    void addData(T data);

    /**
     * Returns all persisted statistics as a file.
     * @return a file with persisted data.
     */
    File getData();
}
