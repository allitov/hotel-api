package com.allitov.hotelapi.model.service.impl;

import com.allitov.hotelapi.message.BookingCreationMessage;
import com.allitov.hotelapi.message.KafkaMessage;
import com.allitov.hotelapi.model.entity.BookingStatistics;
import com.allitov.hotelapi.model.repository.StatisticsRepository;
import com.allitov.hotelapi.model.service.StatisticsService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

/**
 * The {@link StatisticsService} implementation to work with statistics in a database and Kafka.
 * @author allitov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseStatisticsService implements StatisticsService<KafkaMessage> {

    private final StatisticsRepository statisticsRepository;

    /**
     * Persists statistics.
     * @param data a data to persist.
     */
    @KafkaListener(
            topics = {"${app.kafka.topic.user-registration}", "${app.kafka.topic.booking-creation}"},
            groupId = "${app.kafka.group-id.statistics}",
            containerFactory = "listenerContainerFactory"
    )
    @Override
    public void addData(@Payload KafkaMessage data) {
        log.info("Event: {}", data);
        if (data instanceof BookingCreationMessage d) {
            saveBookingStatistics(d);
        }
    }

    /**
     * Returns all persisted statistics as a byte array.
     * @return a byte array with persisted data.
     */
    @Override
    public byte[] getData() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(result))) {
            StatefulBeanToCsv<BookingStatistics> csv = new StatefulBeanToCsvBuilder<BookingStatistics>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            csv.write(statisticsRepository.findAll());
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }

        return result.toByteArray();
    }

    /**
     * Saves booking statistics data.
     * @param message a Kafka message to create booking statistics entity from.
     */
    private void saveBookingStatistics(BookingCreationMessage message) {
        BookingStatistics bookingStatistics = BookingStatistics.builder()
                .id(UUID.randomUUID().toString())
                .userId(message.getUserId())
                .from(message.getFrom())
                .to(message.getTo())
                .build();

        statisticsRepository.save(bookingStatistics);
    }
}
