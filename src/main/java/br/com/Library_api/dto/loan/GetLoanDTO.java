package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.user.GetUsersDTO;

import java.time.LocalDate;

public record GetLoansDTO(Long id, String user, String titleBook,String copy, LocalDate laonDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
    public GetLoansDTO(Loan loan){
        this(loan.getId(),
                loan.getUser().getName(),
                loan.getBookCopy().getBook().getTitle(),
                loan.getBookCopy().getInventoryCode(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getLoanStatus()
        );

    }
}
