package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.fine.Fine;
import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetLoanAndFine(Long loanId, String userName, String titleBook, String copy, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status, int renewals, GetFineSummaryDTO fine) {
    public GetLoanAndFine(Loan loan, Fine fine){
        this(loan.getId(),
                loan.getUser().getName(),
                loan.getBookCopy().getBook().getTitle(),
                loan.getBookCopy().getInventoryCode(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getLoanStatus(),
                loan.getRenewals(),
                fine != null ? new GetFineSummaryDTO(fine.getId(), fine.getAmount(), fine.getIssuedDate(), fine.getPaid()) : null
        );
    }

    public record GetFineSummaryDTO(Long fineId, BigDecimal amount, LocalDate issuedDate, Boolean paid){}
}
