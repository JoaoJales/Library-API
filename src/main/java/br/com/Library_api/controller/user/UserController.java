package br.com.Library_api.controller.user;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.user.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GetDetailingUserDTO> postUser(@RequestBody @Valid UserRegisterDTO data, UriComponentsBuilder uriBuilder){
        var user = userService.createUser(data);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingUserDTO(user));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or #data.id == authentication.principal.id")
    public ResponseEntity<GetDetailingUserDTO> putUser(@RequestBody @Valid PutUserDTO data){
        var user = userService.updateUser(data);

        return ResponseEntity.ok().body(new GetDetailingUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<GetDetailingUserDTO> getDetailingUser(){
        GetDetailingUserDTO detailingUser = userService.getDetailingUser();

        return ResponseEntity.ok(detailingUser);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(){
        userService.deleteUser();

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity alterPassword(@RequestBody @Valid PutPasswordDTO data){
        User user = userService.alterPassword(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/loans")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLoanHistory (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserLoanHistory(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/loans/active")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserActiveLoans (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserActiveLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/loans/late")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLateLoans (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserLateLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/fines/unpaid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getFinesUnpaid (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetFineDTO> page = userService.getFinesUnpaid(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/fines/paid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getFinesPaid (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetFineDTO> page = userService.getFinesPaid(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/reservations")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsByUser(@PageableDefault(size = 10) Pageable pageable){
        Page<GetReservationSummaryDTO> page = userService.getReservationsByUser(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/reservations/actives")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsActivesOrFulfilledByUser(@PageableDefault(size = 10) Pageable pageable){
        Page<GetReservationSummaryDTO> page = userService.getReservationsActivesOrFulfilled(pageable);

        return ResponseEntity.ok().body(page);
    }
}
