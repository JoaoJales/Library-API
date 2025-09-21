package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.user.User;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationUserOrBookInactive implements ValidatorCreateReservation {
    @Override
    public void validate (User user, Book book) {
        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not active");
        }

        if (!book.isActive()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is not active");
        }
    }
}
