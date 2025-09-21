package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Order(3)
public class ValidationRenewAfterDueDate implements ValidatorRenewLoan {
    @Override
    public void validate(Loan loan) {
        if (!loan.getDueDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Cannot renew on or after due date.");
        }
    }
}
