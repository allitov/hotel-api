package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.message.KafkaMessage;
import com.allitov.hotelapi.model.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller class for the booking statistics entity.
 * @author allitov
 */
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService<KafkaMessage> statisticsService;

    @GetMapping
    public ResponseEntity<Resource> getStatistics() {
        byte[] data = statisticsService.getData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("statistics.csv").build());
        headers.setContentLength(data.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(data));
    }
}
