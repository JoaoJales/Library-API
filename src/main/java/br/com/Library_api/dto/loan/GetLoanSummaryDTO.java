package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;

import java.time.LocalDate;

public record GetLoanSummaryDTO(Long id, String bookTitle, String inventoryCode ,
                                LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus loanStatus, int renewals) {


    public GetLoanSummaryDTO(Loan loan){
        this(loan.getId(), loan.getBookCopy().getBook().getTitle(), loan.getBookCopy().getInventoryCode(),
                loan.getLoanDate(), loan.getDueDate(), loan.getReturnDate(), loan.getLoanStatus() ,loan.getRenewals());
    }
}
