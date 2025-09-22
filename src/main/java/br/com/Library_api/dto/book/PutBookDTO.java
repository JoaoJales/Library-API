package br.com.Library_api.dto.book;

import jakarta.validation.constraints.NotNull;

public record PutBookDTO(
        @NotNull
        Long bookId,
        String title,
        Integer publicationYear,
        String publisher,
        String genre
) {
}
