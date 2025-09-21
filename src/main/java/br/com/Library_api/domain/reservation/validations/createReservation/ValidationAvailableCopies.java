package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(6)
public class ValidationAvailableCopies implements ValidatorCreateReservation {
    @Autowired
    private BookCopyRepository bookCopyRepository;


    @Override
    public void validate(User user, Book book) {
        long availableCopies = bookCopyRepository.countAvailableBookCopiesByBook(book.getId());
        if (availableCopies > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot reserve: there are still available copies of this book. Borrow directly from customer service.");
        }
    }
}
