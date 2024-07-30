package com.allitov.hotelapi.configuration;

import com.allitov.hotelapi.message.BookingCreationMessage;
import com.allitov.hotelapi.message.UserRegistrationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return configs;
    }

    @Bean
    public ProducerFactory<String, BookingCreationMessage> bookingCreationProducerFactory(ObjectMapper mapper) {
        return new DefaultKafkaProducerFactory<>(
                producerConfigs(), new StringSerializer(), new JsonSerializer<>(mapper));
    }

    @Bean
    public ProducerFactory<String, UserRegistrationMessage> userRegistrationProducerFactory(ObjectMapper mapper) {
        return new DefaultKafkaProducerFactory<>(
                producerConfigs(), new StringSerializer(), new JsonSerializer<>(mapper));
    }

    @Bean
    public KafkaTemplate<String, BookingCreationMessage> bookingCreationKafkaTemplate(
            ProducerFactory<String, BookingCreationMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, UserRegistrationMessage> userRegistrationKafkaTemplate(
            ProducerFactory<String, UserRegistrationMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
