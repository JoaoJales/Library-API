package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.reservation.ReservationRepository;
import br.com.Library_api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(3)
public class ValidationUserActiveReservationByBook implements ValidatorCreateReservation {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void validate(User user, Book book) {
        if (reservationRepository.existsActiveReservationByUserAndBook(user.getId(), book.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has already reserved this book");
        }
    }
}
