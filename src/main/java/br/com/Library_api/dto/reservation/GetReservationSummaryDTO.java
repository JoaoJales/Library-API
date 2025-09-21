package br.com.Library_api.dto.reservation;

import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationStatus;

import java.time.LocalDateTime;

public record GetReservationSummaryDTO(Long reservationId, Long userId, String title, LocalDateTime reservationDate, LocalDateTime expiredDate, ReservationStatus status) {

    public GetReservationSummaryDTO(Reservation reservation) {
        this(reservation.getId(), reservation.getUser().getId(), reservation.getBook().getTitle(),
                reservation.getCreatedAt(), reservation.getExpiredAt(), reservation.getReservationStatus());
    }
}
