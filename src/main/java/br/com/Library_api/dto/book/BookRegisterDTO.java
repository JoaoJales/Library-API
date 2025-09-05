package br.com.Library_api.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookRegisterDTO(
        @NotBlank
        String title,

        @NotNull
        Integer publicationYear,

        @NotBlank
        String publisher,

        @NotBlank
        String genre,

        @NotBlank
        @Pattern(regexp = "^(97(8|9))\\-?\\d{1,5}\\-?\\d{1,7}\\-?\\d{1,7}\\-?\\d$", message = "The ISBN must be 13 digits and follow the correct format (e.g., 978-1-56619-383-7).")
        String isbn,

        @NotNull
        Long authorId
) {
}
