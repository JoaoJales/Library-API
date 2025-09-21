package br.com.Library_api.domain.loan.validations.createLoan;

import br.com.Library_api.domain.libraryPolicy.LibraryPolicyService;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
@Order(6)
public class ValidationOpeningHoursLoan implements ValidatorCreateLoan {
    @Autowired
    private LibraryPolicyService service;

    @Override
    public void validate(LoanRegisterDTO data) {
        //TODO: Disabled for testing
//        if (!service.isLibraryOpen(LocalDateTime.now())){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Library outside opening hours.");
//        }
    }
}
