package com.allitov.hotelapi.web.dto.request;

import com.allitov.hotelapi.exception.ExceptionMessage;
import com.allitov.hotelapi.model.entity.User;
import com.allitov.hotelapi.web.validation.ValuesOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO request class for the user entity.
 * @author allitov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = ExceptionMessage.USER_BLANK_USERNAME)
    @Schema(example = "username")
    private String username;

    @NotNull(message = ExceptionMessage.USER_NULL_EMAIL)
    @Email(message = ExceptionMessage.USER_INVALID_EMAIL)
    @Schema(example = "email@email.com")
    private String email;

    @NotBlank(message = ExceptionMessage.USER_BLANK_PASSWORD)
    @Schema(example = "password")
    private String password;

    @NotNull(message = ExceptionMessage.USER_NULL_ROLE)
    @ValuesOfEnum(enumClass = User.RoleType.class, message = ExceptionMessage.USER_INVALID_ROLE)
    @Schema(example = "[\"USER\", \"ADMIN\"]", allowableValues = {"USER", "ADMIN"})
    private String role;
}
