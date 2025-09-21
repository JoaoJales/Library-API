package br.com.Library_api.controller.user;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanService;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
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
public class LoanController {
    private final LoanService loanService;

    public LoanController (LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule (admin controller)
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN') and (#data.userId == authentication.principal.id or hasRole('ADMIN'))") // --> test rule
    public ResponseEntity<GetLoanDTO> postLoan(@RequestBody @Valid LoanRegisterDTO data, UriComponentsBuilder uriBuilder) {
        Loan loan = loanService.createLoan(data);

        var uri = uriBuilder.path("/loans/{id}").buildAndExpand(loan.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetLoanDTO(loan));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getLoans (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetLoanDTO> page = loanService.getLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetLoanDTO> getDetailingLoan (@PathVariable Long id) {
        GetLoanDTO dto = loanService.getDetalingLoan(id);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("{id}/return")
//    @PreAuthorize("hasRole('ADMIN')") ---> real business rule (admin controller)
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")  // -->  test rule
    public ResponseEntity<GetLoanDTO> returnLoan(@PathVariable Long id){
        GetLoanDTO dto = loanService.returnLoan(id);

        return ResponseEntity.ok().body(dto);
    }


    @PatchMapping("/{id}/renew")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<GetLoanDTO> renewLoan(@PathVariable Long id) {
        GetLoanDTO dto = loanService.renewLoan(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getActiveLoans (@PageableDefault(size = 10, sort = "loanDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<GetLoanDTO> page = loanService.getActiveLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/late")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getLateLoans (@PageableDefault(size = 10, sort = "loanDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<GetLoanDTO> page = loanService.getLateLoans(pageable);

        return ResponseEntity.ok().body(page);
    }






}
