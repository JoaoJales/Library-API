package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.reservation.ReservationRepository;
import br.com.Library_api.domain.reservation.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(1)
public class ValidationBookReserved implements ValidatorRenewLoan{
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void validate(Loan loan) {
        if (reservationRepository.existsByBookIdAndReservationStatus(loan.getBookCopy().getBook().getId(), ReservationStatus.ACTIVE)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to renew. Another user has already reserved this book.");
        }
    }
}
