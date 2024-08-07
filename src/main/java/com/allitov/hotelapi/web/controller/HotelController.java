package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.filter.HotelFilter;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import com.allitov.hotelapi.web.mapping.HotelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * The controller class for the hotel entity.
 * @author allitov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel controller", description = "Hotel API version 1.0")
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @Operation(
            summary = "Get all hotels",
            description = "Get all hotels. Returns a list of hotels. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of hotels if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = HotelListResponse.class),
                                    mediaType = "application/json"
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
            ),
    })
    @GetMapping
    public ResponseEntity<HotelListResponse> findAll() {
        log.info("Find all request");

        return ResponseEntity.ok(hotelMapper.entityListToListResponse(hotelService.findAll()));
    }

    @Operation(
            summary = "Get hotels by filter",
            description = "Get hotels by filter. Returns a list of hotels with counter. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of hotels with counter " +
                            "if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = HotelListWithCounterResponse.class),
                                    mediaType = "application/json"
                            )
                    }
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
            ),
    })
    @GetMapping("/filter")
    public ResponseEntity<HotelListWithCounterResponse> filterBy(@Valid HotelFilter filter) {
        log.info("Filter by request with filter: '{}'", filter);

        return ResponseEntity.ok(hotelMapper.entityListToListWithCounterResponse(hotelService.filterBy(filter)));
    }

    @Operation(
            summary = "Get hotel by id",
            description = "Get hotel by id. Returns a hotel with requested id. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            parameters = {
                    @Parameter(name = "id", example = "1")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and hotel if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = HotelResponse.class),
                                    mediaType = "application/json"
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
            ),
            @ApiResponse(
                    description = "Returns status 404 and error message if hotel with requested id was not found.",
                    responseCode = "404",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable("id") Integer id) {
        log.info("Find by id request with id = '{}'", id);

        return ResponseEntity.ok(hotelMapper.entityToResponse(hotelService.findById(id)));
    }

    @Operation(
            summary = "Create hotel",
            description = "Create hotel. Returns a created hotel location. " +
                    "Requires any of the authorities: ['ADMIN'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 201 and created hotel location " +
                            "if everything completed successfully.",
                    responseCode = "201",
                    headers = {
                            @Header(name = "Location", description = "A created hotel location")
                    }
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
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody HotelRequest request) {
        log.info("Create request with body = '{}'", request);

        return ResponseEntity.created(
                URI.create("/api/v1/hotel/" + hotelService.create(hotelMapper.requestToEntity(request)).getId())
        ).build();
    }

    @Operation(
            summary = "Update hotel by id",
            description = "Update hotel by id. Returns status 204. " +
                    "Requires any of the authorities: ['ADMIN'].",
            parameters = {
                    @Parameter(name = "id", example = "1")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 204 if everything completed successfully.",
                    responseCode = "204"
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
            ),
            @ApiResponse(
                    description = "Returns status 404 and error message if hotel with requested id was not found.",
                    responseCode = "404",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable("id") Integer id,
                                           @Valid @RequestBody HotelRequest request) {
        log.info("Update by id request with id = '{}' and body = '{}'", id, request);
        hotelService.updateById(id, hotelMapper.requestToEntity(request));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete hotel by id",
            description = "Delete hotel by id. Returns status 204. " +
                    "Requires any of the authorities: ['ADMIN'].",
            parameters = {
                    @Parameter(name = "id", example = "1")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 204 if everything completed successfully.",
                    responseCode = "204"
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        log.info("Delete by id request with id = '{}'", id);
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update hotel rating",
            description = "Update hotel rating. Returns a requested hotel with updated rating. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            parameters = {
                    @Parameter(name = "id", example = "1"),
                    @Parameter(name = "newMark", example = "5")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 if everything completed successfully.",
                    responseCode = "200"
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
            ),
            @ApiResponse(
                    description = "Returns status 404 and error message if hotel with requested id was not found.",
                    responseCode = "404",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<HotelResponse> updateRatingById(@PathVariable("id") Integer id,
                                                          @RequestParam("newMark") Integer newMark) {
        log.info("Update rating by id request with id = '{}' and mark = '{}'", id, newMark);

        return ResponseEntity.ok(hotelMapper.entityToResponse(hotelService.updateRatingById(id, newMark)));
    }
}
