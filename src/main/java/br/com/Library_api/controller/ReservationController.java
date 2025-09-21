package br.com.Library_api.controller;

import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.dto.reservation.GetDetailingReservationDTO;
import br.com.Library_api.dto.reservation.GetLoanFromReservation;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.reservation.PostReservationDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetDetailingReservationDTO> postReservation(@RequestBody @Valid PostReservationDTO data, UriComponentsBuilder uriBuilder) {
        Reservation reservation = reservationService.createReservation(data);

        var uri = uriBuilder.path("/reservations/{id}").buildAndExpand(reservation.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingReservationDTO(reservation));
    }

    @PatchMapping("/{reservationId}/pickup")
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')") // --> test rule
    public ResponseEntity<GetLoanFromReservation> pickUpReservation(@PathVariable Long reservationId) {
        GetLoanFromReservation dto = reservationService.pickUpReservedBook(reservationId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity calcelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);


        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetDetailingReservationDTO> getDetailingReservation (@PathVariable Long id) {
        var dto = reservationService.getDetailingReservation(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservations (@PageableDefault(size = 10)Pageable pageable){
        var page = reservationService.getReservations(pageable);

        return ResponseEntity.ok(page);
    }

}
