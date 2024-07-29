package com.allitov.hotelapi.message;

import lombok.*;

/**
 * Kafka message for user registration event.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRegistrationMessage extends KafkaMessage {

    private Integer userId;
}
