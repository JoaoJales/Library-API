package br.com.Library_api.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AuthorRegisterDTO(
        @NotBlank
        String name,

        @NotBlank
        String nationality,

        @NotBlank
        @Past
        LocalDate birthDate
) {
}
