package br.com.Library_api.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BookRegisterDTO(
        @Schema(example = "O Iluminado")
        @NotBlank
        String title,

        @Schema(example = "1977")
        @NotNull
        Integer publicationYear,

        @Schema(example = "Doubleday")
        @NotBlank
        String publisher,

        @Schema(example = "Terror")
        @NotBlank
        String genre,

        @Schema(example = "9788581050410")
        @NotBlank
        @Pattern(regexp = "^(97(8|9))\\-?\\d{1,5}\\-?\\d{1,7}\\-?\\d{1,7}\\-?\\d$", message = "The ISBN must be 13 digits and follow the correct format (e.g., 978-1-56619-383-7).")
        String isbn,

        @Schema(example = "4")
        @NotNull
        Long authorId
) {
}
