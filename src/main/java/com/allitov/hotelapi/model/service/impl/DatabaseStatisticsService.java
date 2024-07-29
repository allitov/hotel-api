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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @Value("${app.statistics.file-path}")
    private String filePath;

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
     * Returns all persisted statistics as a file.
     * @return a file with persisted data.
     */
    @Override
    public File getData() {
        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<BookingStatistics> csv = new StatefulBeanToCsvBuilder<BookingStatistics>(writer)
                    .withQuotechar('\'')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            csv.write(statisticsRepository.findAll());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }

        return new File(filePath);
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
