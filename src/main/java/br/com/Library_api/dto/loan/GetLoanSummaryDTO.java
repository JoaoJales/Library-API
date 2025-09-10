package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.loan.Loan;

import java.time.LocalDate;

public record GetLoanSummaryDTO(Long id, String username, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, int renewals) {
    public GetLoanSummaryDTO(Loan loan){
        this(loan.getId(), loan.getUser().getName(), loan.getLoanDate(), loan.getDueDate(), loan.getReturnDate(), loan.getRenewals());
    }
}
