package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookAvailabilityDTO(
        @Schema(example = "4")
        Long bookId,
        @Schema(example = "O Iluminado")
        String title,

        @Schema(example = "3")
        Long totalCopies) {
}
