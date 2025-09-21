package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanStatus;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ValidationLoansNotActives implements ValidatorRenewLoan{
    @Override
    public void validate(Loan loan) {
        if (loan.getLoanStatus() != LoanStatus.ACTIVE) {
            throw new IllegalStateException("Only active loans can be renewed.");
        }
    }
}
