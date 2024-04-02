package com.allitov.hotelapi.web.controller;

import com.allitov.hotelapi.model.service.HotelService;
import com.allitov.hotelapi.web.dto.request.HotelRequest;
import com.allitov.hotelapi.web.dto.response.HotelListResponse;
import com.allitov.hotelapi.web.dto.response.HotelResponse;
import com.allitov.hotelapi.web.mapping.HotelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * The controller class for the hotel entity.
 * @author allitov
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel controller", description = "Hotel API version 1.0")
public class HotelController {

    private final HotelService hotelService;

    private final HotelMapper hotelMapper;

    @Operation(
            summary = "Get all hotels",
            description = "Get all hotels. Returns a list of hotels."
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
            )
    })
    @GetMapping
    public ResponseEntity<HotelListResponse> findAll() {
        return ResponseEntity.ok(hotelMapper.entityListToListResponse(hotelService.findAll()));
    }

    @Operation(
            summary = "Get hotel by id",
            description = "Get hotel by id. Returns a hotel with requested id.",
            parameters = {
                    @Parameter(name = "id", example = "1")
            }
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
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hotelMapper.entityToResponse(hotelService.findById(id)));
    }

    @Operation(
            summary = "Create hotel",
            description = "Create hotel. Returns a created hotel location."
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 201 and created hotel location " +
                            "if everything completed successfully.",
                    responseCode = "201",
                    headers = {
                            @Header(name = "Location", description = "A created hotel location")
                    }
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HotelRequest request) {
        return ResponseEntity.created(
                URI.create("/api/v1/hotel/" + hotelService.create(hotelMapper.requestToEntity(request)).getId())
        ).build();
    }

    @Operation(
            summary = "Update hotel by id",
            description = "Update hotel by id. Returns status 204.",
            parameters = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 204 if everything completed successfully.",
                    responseCode = "204"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable("id") Integer id, @RequestBody HotelRequest request) {
        hotelService.updateById(id, hotelMapper.requestToEntity(request));

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete hotel by id",
            description = "Delete hotel by id. Returns status 204.",
            parameters = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses({
            @ApiResponse(
                    description = "Returns status 204 if everything completed successfully.",
                    responseCode = "204"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
