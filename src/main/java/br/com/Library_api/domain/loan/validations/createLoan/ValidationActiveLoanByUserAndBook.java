package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import br.com.Library_api.infra.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Order(5)
public class ValidationActiveLoanByUserAndBook implements ValidatorCreateLoan {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    public void validate(LoanRegisterDTO data) {
        BookCopy bookCopy = bookCopyRepository.findByInventoryCodeAndActiveTrue(data.bookCopyInventoryCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy Not Found or is not active."));

        long userLoggedId = securityService.getLoggedUserId();

        if (loanRepository.existsByUserAndBook(userLoggedId, bookCopy.getBook().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user has a copy of this book on loan");
        }
    }
}
