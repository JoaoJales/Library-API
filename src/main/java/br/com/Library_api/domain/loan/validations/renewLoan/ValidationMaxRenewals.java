package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class ValidationMaxRenewals implements ValidatorRenewLoan{
    @Override
    public void validate(Loan loan) {
        if (loan.getRenewals() >= 2) {
            throw new IllegalStateException("Maximum number of renewals reached.");
        }
    }
}
