package com.allitov.hotelapi.message;

import lombok.*;

import java.time.LocalDate;

/**
 * Kafka message for booking creation event.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BookingCreationMessage extends KafkaMessage {

    private Integer userId;

    private LocalDate from;

    private LocalDate to;
}
