package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationMaxActiveLoansByUser implements ValidatorCreateLoanService{
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(LoanRegisterDTO data) {
        var user = userRepository.findByIdAndActiveTrue(data.userId()).get();

        long activeLoans = loanRepository.countActiveLoansByUser(data.userId());

        if (user.getUserType() == UserType.STUDENT && activeLoans >= 3) {
            throw new IllegalStateException("Student cannot have more than 3 active loans.");
        }

        if (user.getUserType() == UserType.PROFESSOR && activeLoans >= 5) {
            throw new IllegalStateException("Professor cannot have more than 5 active loans.");
        }
    }
}
