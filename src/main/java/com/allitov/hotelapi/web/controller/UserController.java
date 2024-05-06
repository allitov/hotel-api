package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.UserService;
import com.allitov.hotelapi.security.UserDetailsImpl;
import com.allitov.hotelapi.web.dto.request.UserRequest;
import com.allitov.hotelapi.web.dto.response.ErrorResponse;
import com.allitov.hotelapi.web.dto.response.UserListResponse;
import com.allitov.hotelapi.web.dto.response.UserResponse;
import com.allitov.hotelapi.web.mapping.UserMapper;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * The controller class for the user entity.
 * @author allitov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "User controller API version 1.0")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Operation(
            summary = "Get all users",
            description = "Get all users. Returns a list of users. " +
                    "Requires any of the authorities: ['ADMIN'].",
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and list of users if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserListResponse.class),
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
    public ResponseEntity<UserListResponse> findAll() {
        log.info("Find all users");

        return ResponseEntity.ok(userMapper.entityListToListResponse(userService.findAll()));
    }

    @Operation(
            summary = "Get user by id",
            description = "Get user by id. Returns a user with requested id. " +
                    "Requires any of the authorities: ['ADMIN'].",
            parameters = {
                    @Parameter(name = "id", example = "1")
            },
            security = @SecurityRequirement(name = "Basic authorisation")
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 200 and user if everything completed successfully.",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = UserResponse.class),
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
                    description = "Returns status 404 and error message if user with requested id was not found.",
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
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Integer id) {
        log.info("Find by id request with id = '{}'", id);

        return ResponseEntity.ok(userMapper.entityToResponse(userService.findById(id)));
    }

    @Operation(
            summary = "Create user",
            description = "Create user. Returns a created user location."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 201 and created user location " +
                            "if everything completed successfully.",
                    responseCode = "201",
                    headers = {
                            @Header(name = "Location", description = "A created user location")
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
                    description = "Returns status 409 and error message " +
                            "if request contains already existing username.",
                    responseCode = "409",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody UserRequest request) {
        log.info("Create request with body = '{}'", request);

        return ResponseEntity.created(
                URI.create("/api/v1/user/" + userService.create(userMapper.requestToEntity(request)).getId())
        ).build();
    }

    @Operation(
            summary = "Update user by id",
            description = "Update user by id. Returns status 204. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
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
                            "if user with requested id was not found.",
                    responseCode = "404",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    description = "Returns status 409 and error message " +
                            "if request contains already existing username.",
                    responseCode = "409",
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
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @Valid @RequestBody UserRequest request) {
        log.info("Update by id request with id = '{}' and body = '{}'", id, request);
        userService.updateById(id, userMapper.requestToEntity(request));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete user by id",
            description = "Delete user by id. Returns status 204. " +
                    "Requires any of the authorities: ['ADMIN', 'USER'].",
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
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Delete by id request with id = '{}'", id);
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
