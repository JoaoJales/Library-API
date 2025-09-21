package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.user.User;

public interface ValidatorCreateReservation {
    void validate (User user, Book book);
}
