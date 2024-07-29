package com.allitov.hotelapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * The class that represents an entity of a booking statistics.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookingStatistics")
public class BookingStatistics {

    @Id
    private String id;

    private Integer userId;

    private LocalDate from;

    private LocalDate to;
}
