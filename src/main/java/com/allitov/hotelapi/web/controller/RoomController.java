package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.RoomService;
import com.allitov.hotelapi.web.dto.filter.RoomFilter;
import com.allitov.hotelapi.web.dto.request.RoomRequest;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.allitov.hotelapi.web.dto.response.RoomListResponse;
import com.allitov.hotelapi.web.dto.response.RoomListWithCounterResponse;
import com.allitov.hotelapi.web.dto.response.RoomResponse;
import com.allitov.hotelapi.web.mapping.RoomMapper;
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
 * The controller class for the room entity.
 * @author allitov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@Tag(name = "Room controller", description = "Room API version 1.0")
public class RoomController {

    private final RoomService roomService;

    private final RoomMapper roomMapper;

    @Operation(
            summary = "Get all rooms",
            description = "Get all rooms. Returns a list of rooms. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of rooms if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = RoomListResponse.class),
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
    @GetMapping
    public ResponseEntity<RoomListResponse> findAll() {
        log.info("Find all request");

        return ResponseEntity.ok(roomMapper.entityListToListResponse(roomService.findAll()));
    }

    @Operation(
            summary = "Get rooms by filter",
            description = "Get rooms by filter. Returns a list of rooms with counter. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of rooms with counter " +
                            "if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = RoomListWithCounterResponse.class),
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
            )
    })
    @GetMapping("/filter")
    public ResponseEntity<RoomListWithCounterResponse> filterBy(@Valid RoomFilter filter) {
        log.info("Filter by request with filter: '{}'", filter);

        return ResponseEntity.ok(roomMapper.entityListToListWithCounterResponse(roomService.filterBy(filter)));
    }

    @Operation(
            summary = "Get room by id",
            description = "Get room by id. Returns a room with requested id. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
            parameters = {
                    @Parameter(name = "id", example = "1")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and room if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = RoomResponse.class),
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
                    description = "Returns status 404 and error message if room with requested id was not found.",
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
    public ResponseEntity<RoomResponse> findById(@PathVariable("id") Integer id) {
        log.info("Find by id request with id = '{}'", id);

        return ResponseEntity.ok(roomMapper.entityToResponse(roomService.findById(id)));
    }

    @Operation(
            summary = "Create room",
            description = "Create room. Returns a created room location. " +
                    "Requires any of the authorities: ['ADMIN'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 201 and created room location " +
                            "if everything completed successfully.",
                    responseCode = "201",
                    headers = {
                            @Header(name = "Location", description = "A created room location")
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
            @ApiResponse(
                    description = "Returns status 404 and error message if request contains nonexistent hotel id.",
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
    public ResponseEntity<Void> create(@Valid @RequestBody RoomRequest request) {
        log.info("Create request with body = '{}'", request);

        return ResponseEntity.created(
                URI.create("/api/v1/room/" + roomService.create(roomMapper.requestToEntity(request)).getId())
        ).build();
    }

    @Operation(
            summary = "Update room by id",
            description = "Update room by id. Returns status 204. " +
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
                    description = "Returns status 404 and error message " +
                            "if room with requested id was not found or request contains nonexistent hotel id.",
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
                                           @Valid @RequestBody RoomRequest request) {
        log.info("Update by id request with id = '{}' and body = '{}'", id, request);
        roomService.updateById(id, roomMapper.requestToEntity(request));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete room by id",
            description = "Delete room by id. Returns status 204. " +
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
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
