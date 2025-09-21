package br.com.Library_api.dto.reservation;

import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationStatus;

import java.time.LocalDateTime;

public record GetDetailingReservationDTO(Long reservationId, Long userId, String name, Long bookId, String title, LocalDateTime reservationDate, LocalDateTime expiredDate, ReservationStatus status) {
    public GetDetailingReservationDTO(Reservation reservation) {
        this(reservation.getId(), reservation.getUser().getId(), reservation.getUser().getName(), reservation.getBook().getId(), reservation.getBook().getTitle(),
        reservation.getCreatedAt(), reservation.getExpiredAt(), reservation.getReservationStatus()
        );
    }
}
