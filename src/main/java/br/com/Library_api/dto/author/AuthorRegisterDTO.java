package br.com.Library_api.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AuthorRegisterDTO(
        @NotBlank
        String name,

        @NotBlank
        String nationality,

        @NotNull
        @Past
        LocalDate birthDate
) {
}
