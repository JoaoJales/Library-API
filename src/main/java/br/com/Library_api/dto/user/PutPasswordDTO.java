package br.com.Library_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PutPasswordDTO(
        @NotBlank
        String oldPassword,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "New password must have at least 8 characters, including one letter and one number."
        )
        String newPassword
) {
}
