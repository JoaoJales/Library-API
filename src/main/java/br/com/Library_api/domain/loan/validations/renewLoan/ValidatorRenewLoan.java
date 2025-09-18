package br.com.Library_api.domain.loan.validations.renewLoan;

import br.com.Library_api.domain.loan.Loan;

public interface ValidatorRenewLoan {
    void validate(Loan loan);
}
