package br.com.Library_api.controller;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetUsersDTO>> getUsers (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetUsersDTO> page = userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<GetDetailingUserDTO> getDetailingUser(@PathVariable Long id){
        GetDetailingUserDTO detailingUser = userService.getDetailingUser(id);

        return ResponseEntity.ok(detailingUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity alterPassword(@RequestBody @Valid PutPasswordDTO data){
        User user = userService.alterPassword(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/loans")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLoanHistory (@PageableDefault(size = 10) Pageable pageable ,@PathVariable Long id) {
        Page<GetLoanSummaryDTO> page = userService.getUserLoanHistory(pageable,id);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/loans/active")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserActiveLoans (@PageableDefault(size = 10) Pageable pageable ,@PathVariable Long id) {
        Page<GetLoanSummaryDTO> page = userService.getUserActiveLoans(pageable,id);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/loans/late")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLateLoans (@PageableDefault(size = 10) Pageable pageable ,@PathVariable Long id) {
        Page<GetLoanSummaryDTO> page = userService.getUserLateLoans(pageable,id);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/fines/unpaid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    public ResponseEntity<Page<GetFineDTO>> getFinesUnpaid (@PageableDefault(size = 10) Pageable pageable, @PathVariable Long id) {
        Page<GetFineDTO> page = userService.getFinesUnpaid(pageable, id);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}/fines/paid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    public ResponseEntity<Page<GetFineDTO>> getFinesPaid (@PageableDefault(size = 10) Pageable pageable, @PathVariable Long id) {
        Page<GetFineDTO> page = userService.getFinesPaid(pageable, id);

        return ResponseEntity.ok().body(page);
    }
}
