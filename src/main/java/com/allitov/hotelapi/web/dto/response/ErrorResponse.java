package com.allitov.hotelapi.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO response class for errors.
 * @author allitov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(example = "Some error")
    private String errorMessage;
}
