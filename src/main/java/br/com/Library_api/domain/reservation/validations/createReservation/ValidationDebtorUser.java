package br.com.Library_api.domain.reservation.validations.createReservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.fine.FineRepository;
import br.com.Library_api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(2)
public class ValidationDebtorUser implements ValidatorCreateReservation{
    @Autowired
    private FineRepository fineRepository;

    @Override
    public void validate(User user, Book book) {
        if (fineRepository.existsByLoan_UserIdAndPaid(user.getId(), false)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This user has an outstanding fine. Please pay your fine to be eligible to make a reservation..");
        }
    }
}
