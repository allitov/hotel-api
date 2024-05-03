package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.BookingService;
import com.allitov.hotelapi.web.dto.request.BookingRequest;
import com.allitov.hotelapi.web.dto.response.BookingListResponse;
import com.allitov.hotelapi.web.dto.response.BookingResponse;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.allitov.hotelapi.web.mapping.BookingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Booking controller", description = "Booking API version 1.0")
public class BookingController {

    private final BookingService bookingService;

    private final BookingMapper bookingMapper;

    @Operation(
            summary = "Get all bookings",
            description = "Get all bookings. Returns a list of bookings."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of bookings if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = BookingListResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<BookingListResponse> findAll() {
        log.info("Find all request");

        return ResponseEntity.ok(bookingMapper.entityListToListResponse(bookingService.findAll()));
    }

    @Operation(
            summary = "Create booking",
            description = "Create booking. Returns a created booking."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 201 and created booking " +
                            "if everything completed successfully.",
                    responseCode = "201"
            ),
            @ApiResponse(
                    description = "Returns status 400 and error message if request has invalid values.",
                    responseCode = "400",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    description = "Returns status 404 and error message " +
                            "if request contains nonexistent user id or room id.",
                    responseCode = "404",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest request) {
        log.info("Create request with body = '{}'", request);
        BookingResponse response = bookingMapper.entityToResponse(
                bookingService.create(bookingMapper.requestToEntity(request)));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
