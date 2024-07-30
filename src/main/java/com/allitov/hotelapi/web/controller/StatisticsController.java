package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.message.KafkaMessage;
import com.allitov.hotelapi.model.service.StatisticsService;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics controller", description = "Statistics API version 1.0")
public class StatisticsController {

    private final StatisticsService<KafkaMessage> statisticsService;

    @Operation(
            summary = "Get statistics",
            description = "Get statistics. Returns a CSV file with statistics. " +
                    "Requires any of the authorities: ['ADMIN'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and a CSV file with statistics " +
                            "if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/octet-stream"
                            )
                    }
            ),
            @ApiResponse(
                    description = "Returns status 401 and error message if user is not authorized.",
                    responseCode = "401",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    description = "Returns status 403 and error message if user has no required authorities.",
                    responseCode = "403",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<Resource> getStatistics() {
        log.info("Get statistics request");

        byte[] data = statisticsService.getData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("statistics.csv").build());
        headers.setContentLength(data.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(data));
    }
}
