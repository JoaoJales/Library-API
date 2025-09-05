package br.com.Library_api.dto.loan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.dto.bookCopy.GetBookCopysDTO;
import br.com.Library_api.dto.user.GetUsersDTO;

import java.time.LocalDate;

public record GetLoansDTO(Long id, GetUsersDTO user, GetBookCopysDTO copy, LocalDate laonDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
    public GetLoansDTO(Loan loan){
        this(loan.getId(),
                new GetUsersDTO(loan.getUser().getId(), loan.getUser().getName(), loan.getUser().getEmail(), loan.getUser().getPhone(), loan.getUser().getUserType()),

                new GetBookCopysDTO(loan.getBookCopy().getId(), loan.getBookCopy().getBook().getTitle(), loan.getBookCopy().getInventoryCode(), loan.getBookCopy().getAvailable()),

                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getLoanStatus()
        );

    }
}
