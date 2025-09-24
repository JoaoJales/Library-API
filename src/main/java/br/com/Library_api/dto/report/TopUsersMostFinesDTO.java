package br.com.Library_api.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;

public record TopUsersMostFinesDTO(
        @Schema(example = "9")
        Long userId,

        @Schema(example = "Beatriz Martins")
        String name,

        @Schema(example = "beatriz.martins@hotmail.com")
        String email,

        @Schema(example = "4")
        Long totalFines) {
}
