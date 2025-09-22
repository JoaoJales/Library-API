package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.dto.reservation.GetLoanFromReservation;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reservations")
public class AdminReservationController {
    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //TODO: disabled for tests
//    @PatchMapping("/{reservationId}/pickup")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<GetLoanFromReservation> pickUpReservation(@PathVariable Long reservationId) {
//        GetLoanFromReservation dto = reservationService.pickUpReservedBook(reservationId);
//        return ResponseEntity.ok(dto);
//    }


    @GetMapping
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservations (@PageableDefault(size = 10)Pageable pageable){
        var page = reservationService.getReservations(pageable);

        return ResponseEntity.ok(page);
    }

}
