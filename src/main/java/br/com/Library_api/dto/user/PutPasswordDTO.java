package br.com.Library_api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PutPasswordDTO(
        @Schema(example = "past1234")
        @NotBlank
        String oldPassword,


        @Schema(example = "new123456")
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "New password must have at least 8 characters, including one letter and one number."
        )
        String newPassword
) {
}
