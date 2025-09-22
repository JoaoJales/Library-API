package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanService;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin/loans")
public class AdminLoanController {
    private final LoanService loanService;

    public AdminLoanController(LoanService loanService){
        this.loanService = loanService;
    }

    //TODO: disabled for common user testing
//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<GetLoanDTO> postLoan(@RequestBody @Valid LoanRegisterDTO data, UriComponentsBuilder uriBuilder) {
//        Loan loan = loanService.createLoan(data);
//
//        var uri = uriBuilder.path("/loans/{id}").buildAndExpand(loan.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(new GetLoanDTO(loan));
//    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetLoanDTO>> getLoans (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetLoanDTO> page = loanService.getLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    //TODO: disabled for common user testing
//    @PatchMapping("{id}/return")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<GetLoanDTO> returnLoan(@PathVariable Long id){
//        GetLoanDTO dto = loanService.returnLoan(id);
//
//        return ResponseEntity.ok().body(dto);
//    }


}
