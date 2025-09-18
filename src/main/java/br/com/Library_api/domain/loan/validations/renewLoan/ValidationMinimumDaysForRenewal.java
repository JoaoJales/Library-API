package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Component
public class ValidationMinimumDaysForRenewal implements ValidatorRenewLoan {
    @Override
    public void validate(Loan loan) {
        if (loan.getDueDate().isAfter(LocalDate.now().plusDays(3))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only renew your loan within 3 days of due date.");
        }
    }
}
