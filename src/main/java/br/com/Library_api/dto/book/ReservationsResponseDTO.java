package br.com.Library_api.dto.book;

import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import org.springframework.data.domain.Page;

public record ReservationsResponseDTO(ReservationDataSummaryDTO summary, Page<GetReservationSummaryDTO> content) {

    public record ReservationDataSummaryDTO(Long totalReservations, Long totalActivesReservations){}
}
