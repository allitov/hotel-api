package com.allitov.hotelapi.model.repository;

import com.allitov.hotelapi.model.entity.BookingStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Provides methods for working with a booking statistics entity in a database.
 * @author allitov
 */
public interface StatisticsRepository extends MongoRepository<BookingStatistics, String> {
}
