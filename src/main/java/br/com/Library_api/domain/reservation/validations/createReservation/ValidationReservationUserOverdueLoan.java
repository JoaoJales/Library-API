package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.domain.loan.validations.createLoan.ValidatorCreateLoan;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ValidationReservationUserOverdueLoan implements ValidatorCreateReservation {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void validate(User user, Book book) {
        if (loanRepository.existsByLoanStatusAndUserIdAndUserActive(LoanStatus.LATE, user.getId())){
            throw new IllegalStateException("This user has an overdue loan. Please return the book and pay the fine to be eligible for a new reservation.");
        }
    }
}
