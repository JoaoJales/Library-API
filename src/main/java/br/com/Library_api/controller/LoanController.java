package br.com.Library_api.controller;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanService;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GetLoanDTO> postLoan(@RequestBody @Valid LoanRegisterDTO data, UriComponentsBuilder uriBuilder) {
        Loan loan = loanService.createLoan(data);

        var uri = uriBuilder.path("/loans/{id}").buildAndExpand(loan.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetLoanDTO(loan));
    }

    @GetMapping
    public ResponseEntity<Page<GetLoanDTO>> getLoans (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetLoanDTO> page = loanService.getLoans(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetLoanDTO> getDetailingLoan (@PathVariable Long id) {
        GetLoanDTO dto = loanService.getDetalingLoan(id);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("{id}/return")
    public ResponseEntity<GetLoanDTO> returnLoan(@PathVariable Long id){
        GetLoanDTO dto = loanService.returnLoan(id);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("{id}/late")
    public ResponseEntity<GetLoanDTO> lateLoan(@PathVariable Long id){
        GetLoanDTO dto = loanService.setlateLoan(id);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}/renew")
    public ResponseEntity<GetLoanDTO> renewLoan(@PathVariable Long id) {
        GetLoanDTO dto = loanService.renewLoan(id);
        return ResponseEntity.ok().body(dto);
    }






}
