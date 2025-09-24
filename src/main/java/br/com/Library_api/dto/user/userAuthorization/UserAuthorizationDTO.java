package br.com.Library_api.dto.user.userAuthorization;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthorizationDTO(
        @Schema(example = "example@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(example = "senha123")
        @NotBlank
        String password
) {
}
