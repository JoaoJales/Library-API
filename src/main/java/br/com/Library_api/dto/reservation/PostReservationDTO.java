package br.com.Library_api.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PostReservationDTO(
        @Schema(example = "12")
        @NotNull
        Long bookId
) {
}
