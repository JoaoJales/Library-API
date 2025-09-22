package br.com.Library_api.dto.author;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PutAuthorDTO(
        @NotNull
        Long authorId,
        String name,
        String nationality,
        @Past
        LocalDate birthDate
) {
}
