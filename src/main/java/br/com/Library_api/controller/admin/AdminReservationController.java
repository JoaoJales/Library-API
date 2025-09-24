package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.dto.reservation.GetLoanFromReservation;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reservations")
@SecurityRequirement(name = "bearer-key")
public class AdminReservationController {
    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //TODO: disabled for tests
//    @Operation(summary = "Confirmar busca de uma reserva", tags = {"9 - Admin"})
//    @PatchMapping("/{reservationId}/pickup")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<GetLoanFromReservation> pickUpReservation(@PathVariable Long reservationId) {
//        GetLoanFromReservation dto = reservationService.pickUpReservedBook(reservationId);
//        return ResponseEntity.ok(dto);
//    }


    @Operation(summary = "Consultar todas as reservas", tags = {"9 - Admin"})
    @GetMapping
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservations (@PageableDefault(size = 10)Pageable pageable){
        var page = reservationService.getReservations(pageable);

        return ResponseEntity.ok(page);
    }

}
