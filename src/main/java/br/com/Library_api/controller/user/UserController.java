package br.com.Library_api.controller.user;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.user.GetDetailingUserDTO;
import br.com.Library_api.dto.user.PutPasswordDTO;
import br.com.Library_api.dto.user.PutUserDTO;
import br.com.Library_api.dto.user.UserRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Cadastro de usuário", tags = {"1.1 - Cadastro"})
    @PostMapping("/register")
    public ResponseEntity<GetDetailingUserDTO> postUser(@RequestBody @Valid UserRegisterDTO data, UriComponentsBuilder uriBuilder){
        var user = userService.createUser(data);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingUserDTO(user));
    }

    @Operation(summary = "Atualização de dados", tags = {"2.1 - Users - Conta"})
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or #data.id == authentication.principal.id")
    public ResponseEntity<GetDetailingUserDTO> putUser(@RequestBody @Valid PutUserDTO data){
        var user = userService.updateUser(data);

        return ResponseEntity.ok().body(new GetDetailingUserDTO(user));
    }

    @Operation(summary = "Consultar detalhes de usuário", description = "Busca informações detalhadas do usuário autenticado.", tags = {"2.1 - Users - Conta"})
    @GetMapping
    public ResponseEntity<GetDetailingUserDTO> getDetailingUser(){
        GetDetailingUserDTO detailingUser = userService.getDetailingUser();

        return ResponseEntity.ok(detailingUser);
    }

    @Operation(summary = "Deletar usuário", description = "Deleta o usuário (Soft delete)", tags = {"2.1 - Users - Conta"})
    @DeleteMapping
    public ResponseEntity deleteUser(){
        userService.deleteUser();

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar senha", tags = {"2.1 - Users - Conta"})
    @PutMapping("/password")
    public ResponseEntity alterPassword(@RequestBody @Valid PutPasswordDTO data){
        User user = userService.alterPassword(data);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Consultar histórico de empréstimos", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/loans")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLoanHistory (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserLoanHistory(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar empréstimos ativos", description = "Retorna uma página dos empréstimos ativos do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/loans/actives")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserActiveLoans (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserActiveLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar empréstimos atrasados", description = "Retorna uma página dos empréstimos atrasados do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/loans/late")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanSummaryDTO>> getUserLateLoans (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetLoanSummaryDTO> page = userService.getUserLateLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar multas não pagas", description = "Retorna uma página dos multas não pagas do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/fines/unpaid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getFinesUnpaid (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetFineDTO> page = userService.getFinesUnpaid(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar multas pagas", description = "Retorna uma página dos multas pagas do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/fines/paid")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getFinesPaid (@PageableDefault(size = 10) Pageable pageable) {
        Page<GetFineDTO> page = userService.getFinesPaid(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar histórico de reservas", description = "Retorna uma página com o histórico de reservas do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/reservations")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsByUser(@PageableDefault(size = 10) Pageable pageable){
        Page<GetReservationSummaryDTO> page = userService.getReservationsByUser(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar reservas ativas ou prontas", description = "Retorna uma página com as reservas ativas ou prontas para retirada do usuário", tags = {"2.2 - Users - Consultas"})
    @GetMapping("/reservations/actives")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsActivesOrFulfilledByUser(@PageableDefault(size = 10) Pageable pageable){
        Page<GetReservationSummaryDTO> page = userService.getReservationsActivesOrFulfilled(pageable);

        return ResponseEntity.ok().body(page);
    }
}
