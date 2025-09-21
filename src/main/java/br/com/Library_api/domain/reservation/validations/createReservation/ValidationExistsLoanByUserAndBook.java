package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(4)
public class ValidationExistsLoanByUserAndBook implements ValidatorCreateReservation {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void validate(User user, Book book) {
        if (loanRepository.existsByUserAndBook(user.getId(), book.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have an active loan of this book");
        }
    }
}
