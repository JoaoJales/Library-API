package br.com.Library_api.controller.user;

import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.dto.reservation.GetDetailingReservationDTO;
import br.com.Library_api.dto.reservation.GetLoanFromReservation;
import br.com.Library_api.dto.reservation.PostReservationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reservations")
@Tag(name = "6 - Reservations")
@SecurityRequirement(name = "bearer-key")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Registrar Reserva")
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetDetailingReservationDTO> postReservation(@RequestBody @Valid PostReservationDTO data, UriComponentsBuilder uriBuilder) {
        Reservation reservation = reservationService.createReservation(data);

        var uri = uriBuilder.path("/reservations/{id}").buildAndExpand(reservation.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingReservationDTO(reservation));
    }

    @Operation(summary = "Confirmar entrega de livro de uma Reserva")
    @PatchMapping("/{reservationId}/pickup")
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule (admin controller)
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')") // --> test rule
    public ResponseEntity<GetLoanFromReservation> pickUpReservation(@PathVariable Long reservationId) {
        GetLoanFromReservation dto = reservationService.pickUpReservedBook(reservationId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Cancelar Reserva")
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity calcelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);


        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Consultar detalhes de uma Reserva")
    @GetMapping("{reservationId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetDetailingReservationDTO> getDetailingReservation (@PathVariable Long reservationId) {
        var dto = reservationService.getDetailingReservation(reservationId);

        return ResponseEntity.ok(dto);
    }

}
