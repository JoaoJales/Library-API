package br.com.Library_api.controller.user;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanService;
import br.com.Library_api.dto.loan.GetLoanAndFine;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/loans")
@Tag(name = "5 - Loans")
@SecurityRequirement(name = "bearer-key")
public class LoanController {
    private final LoanService loanService;

    public LoanController (LoanService loanService){
        this.loanService = loanService;
    }

    @Operation(summary = "Registar um Empréstimo")
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule (admin controller)
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#data.userId == authentication.principal.id or hasRole('ADMIN'))") // --> test rule
    public ResponseEntity<GetLoanDTO> postLoan(@RequestBody @Valid LoanRegisterDTO data, UriComponentsBuilder uriBuilder) {
        Loan loan = loanService.createLoan(data);

        var uri = uriBuilder.path("/loans/{id}").buildAndExpand(loan.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetLoanDTO(loan));
    }

    @Operation(summary = "Consultar detalhes de um Empréstimo")
    @GetMapping("{loanId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetLoanDTO> getDetailingLoan (@PathVariable Long loanId) {
        GetLoanDTO dto = loanService.getDetalingLoan(loanId);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Confirmar retorno do livro de um empréstimo")
    @PatchMapping("{loanId}/return")
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule (admin controller)
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")  // -->  test rule
    public ResponseEntity<GetLoanAndFine> returnLoan(@PathVariable Long loanId){
        GetLoanAndFine dto = loanService.returnLoan(loanId);

        return ResponseEntity.ok().body(dto);
    }


    @Operation(summary = "Renovar Empréstimo")
    @PatchMapping("/{loanId}/renew")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetLoanDTO> renewLoan(@PathVariable Long loanId) {
        GetLoanDTO dto = loanService.renewLoan(loanId);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Consultar Empréstimos ativos")
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getActiveLoans (@PageableDefault(size = 10, sort = "loanDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<GetLoanDTO> page = loanService.getActiveLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar Empréstimos atrasados")
    @GetMapping("/late")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getLateLoans (@PageableDefault(size = 10, sort = "loanDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<GetLoanDTO> page = loanService.getLateLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

}
