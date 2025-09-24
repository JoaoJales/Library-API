package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record TopMostReadAuthorsDTO(
        @Schema(example = "9")
        Long id,

        @Schema(example = "Agatha Christie")
        String name,

        @Schema(example = "Britânica")
        String nationality,

        @Schema(example = "1")
        Long totalBooksRegistered,

        @Schema(example = "9")
        Long totalLoans

) {
}
