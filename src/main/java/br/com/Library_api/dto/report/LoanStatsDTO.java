package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanStatsDTO(
        @Schema(example = "1")
        int month,
        @Schema(example = "9")
        long totalLoans) {
}
