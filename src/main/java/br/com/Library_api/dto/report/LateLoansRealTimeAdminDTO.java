package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record LateLoansRealTimeAdminDTO(
        @Schema(example = "2")
        Long loanId,

        @Schema(example = "Maria Silva")
        String userName,

        @Schema(example = "Orgulho e Preconceito")
        String titleBook,

        @Schema(example = "JA-OP-1813-001")
        String copy,

        @Schema(example = "2025-09-01")
        LocalDate loanDate,

        @Schema(example = "2025-09-15")
        LocalDate dueDate
) {
}
