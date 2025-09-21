package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.fine.FineRepository;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ValidationUserUnpaidFine implements ValidatorCreateLoan {
    @Autowired
    private FineRepository fineRepository;

    @Override
    public void validate(LoanRegisterDTO data) {
        if (fineRepository.existsByLoan_UserIdAndPaid(data.userId(), false)){
            throw new IllegalStateException("This user has an outstanding fine. Please pay your fine to be eligible for a new loan.");
        }

    }
}
