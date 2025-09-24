package br.com.Library_api.dto.author;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AuthorRegisterDTO(
        @Schema(example = "J.K. Rowling")
        @NotBlank
        String name,

        @Schema(example = "Britânica")
        @NotBlank
        String nationality,

        @Schema(example = "1965-07-31")
        @NotNull
        @Past
        LocalDate birthDate
) {
}
