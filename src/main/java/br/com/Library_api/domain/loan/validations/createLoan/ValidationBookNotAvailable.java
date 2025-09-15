package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.book.BookRepository;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationBookNotAvailable implements ValidatorCreateLoanService{
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
