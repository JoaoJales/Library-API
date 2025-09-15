package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.dto.loan.LoanRegisterDTO;

public interface ValidatorCreateLoanService {
    void validate (LoanRegisterDTO data);
}
