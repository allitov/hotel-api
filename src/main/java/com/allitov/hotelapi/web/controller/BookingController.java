package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.BookingService;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.BookingListResponse;
import com.allitov.hotelapi.web.dto.response.BookingResponse;
import com.allitov.hotelapi.web.mapping.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller class for the booking entity.
 * @author allitov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @GetMapping
    public ResponseEntity<BookingListResponse> findAll() {
        log.info("Find all request");

        return ResponseEntity.ok(bookingMapper.entityListToListResponse(bookingService.findAll()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
        log.info("Create request with body = '{}'", request);
        BookingResponse response = bookingMapper.entityToResponse(
                bookingService.create(bookingMapper.requestToEntity(request)));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
