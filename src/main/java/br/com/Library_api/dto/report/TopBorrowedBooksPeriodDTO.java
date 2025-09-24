package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record TopBorrowedBooksPeriodDTO(
        @Schema(example = "5")
        Long bookId,

        @Schema(example = "A Hora da Estrela")
        String title,

        @Schema(example = "Clarice Lispector")
        String authorName,

        @Schema(example = "2")
        long borrowCount) {
}
