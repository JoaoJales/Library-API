package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUserOverdueLoan implements ValidatorCreateLoanService{
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void validate(LoanRegisterDTO data) {
        if (loanRepository.existsByLoanStatusAndUserIdAndUserActive(LoanStatus.LATE, data.userId())){
            throw new IllegalStateException("This user has an overdue loan. Please return the book and pay the fine to be eligible for a new loan.");
        }
    }
}
