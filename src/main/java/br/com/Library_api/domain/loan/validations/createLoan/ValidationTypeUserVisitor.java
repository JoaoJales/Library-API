package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ValidationTypeUserVisitor implements ValidatorCreateLoanService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(LoanRegisterDTO data) {
        var user = userRepository.findByIdAndActiveTrue(data.userId()).get();

        if (user.getUserType() == UserType.VISITOR){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Visitors are not allowed to take out loans.");
        }
    }
}
