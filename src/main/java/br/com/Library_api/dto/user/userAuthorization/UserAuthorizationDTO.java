package br.com.Library_api.dto.user.userAuthorization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthorizationDTO(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
