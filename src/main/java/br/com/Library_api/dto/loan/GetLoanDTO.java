package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;

import java.time.LocalDate;

public record GetLoanDTO(Long loanId, String userName, String titleBook, String copy, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status, int renewals) {
    public GetLoanDTO(Loan loan){
        this(loan.getId(),
                loan.getUser().getName(),
                loan.getBookCopy().getBook().getTitle(),
                loan.getBookCopy().getInventoryCode(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getLoanStatus(),
                loan.getRenewals()
        );

    }
}
