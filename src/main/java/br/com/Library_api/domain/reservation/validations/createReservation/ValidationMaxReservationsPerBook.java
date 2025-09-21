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
@Order(7)
public class ValidationMaxReservationsPerBook implements ValidatorCreateReservation{
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void validate(User user, Book book) {
        long countReservations = reservationRepository.countReservationsByBook(book.getId());

        if (countReservations >= 10){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with maximum reservations reached");
        }
    }
}
