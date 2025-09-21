package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ValidationBookNotAvailable implements ValidatorCreateLoan {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public void validate(LoanRegisterDTO data) {
        var bookCopy = bookCopyRepository.findByInventoryCodeAndActiveTrue(data.bookCopyInventoryCode()).get();


        if (!bookCopy.getAvailable()){
            throw new IllegalStateException("This book copy is not available.");
        }
    }
}
