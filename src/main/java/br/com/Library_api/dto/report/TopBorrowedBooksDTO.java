package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record TopBorrowedBooksDTO(
        @Schema(example = "12")
        Long bookId,

        @Schema(example = "Crime e Castigo")
        String title,

        @Schema(example = "Fiódor Dostoiévski")
        String authorName,

        @Schema(example = "21")
        Long loanCount
) {
}
