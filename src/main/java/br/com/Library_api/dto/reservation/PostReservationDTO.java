package br.com.Library_api.dto.reservation;

import jakarta.validation.constraints.NotNull;

public record PostReservationDTO(
        @NotNull
        Long bookId
) {
}
