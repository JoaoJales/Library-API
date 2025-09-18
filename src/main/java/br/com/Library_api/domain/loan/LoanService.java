package br.com.Library_api.domain.loan;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.domain.loan.validations.createLoan.ValidatorCreateLoanService;
import br.com.Library_api.domain.loan.validations.renewLoan.ValidatorRenewLoan;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import br.com.Library_api.infra.security.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;
    private final FineService fineService;
    private final List<ValidatorCreateLoanService> validatorsCreateLoan;
    private final List<ValidatorRenewLoan> validatorsRenewLoan;
    private final SecurityService securityService;

    public LoanService (LoanRepository loanRepository, UserRepository userRepository,
                        BookCopyRepository bookCopyRepository, FineService fineService,
                        List<ValidatorCreateLoanService> validatorsCreateLoan, List<ValidatorRenewLoan> validatorsRenewLoan,
                        SecurityService securityService){
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.fineService = fineService;
        this.validatorsCreateLoan = validatorsCreateLoan;
        this.validatorsRenewLoan = validatorsRenewLoan;
        this.securityService = securityService;
    }

    @Transactional
    public Loan createLoan(LoanRegisterDTO data) {
        BookCopy bookCopy = bookCopyRepository.findByInventoryCodeAndActiveTrue(data.bookCopyInventoryCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy Not Found or is not active."));

        User user = userRepository.findByIdAndActiveTrue(data.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found or is not active."));

        validatorsCreateLoan.forEach(v -> v.validate(data));

        Loan loan = new Loan(data, user ,bookCopy);
        bookCopy.bookCopyNotAvailable();
        bookCopyRepository.save(bookCopy);

        loanRepository.save(loan);

        return loan;
    }

    public Page<GetLoanDTO> getLoans(Pageable pageable) {
        return loanRepository.findAllByEntitiesActives(pageable).map(GetLoanDTO::new);
    }


    public GetLoanDTO getDetalingLoan(Long id) {
        Loan loan = findLoan(id);
        verifyLoanBelongsUserLogged(loan);

        return new GetLoanDTO(loan);
    }

    @Transactional
    public GetLoanDTO returnLoan (Long id){
        Loan loan = findLoan(id);
        if (loan.getLoanStatus() == LoanStatus.RETURNED){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Loan already returned");
        }
        verifyLoanBelongsUserLogged(loan);

        loan.returnLoan();
        BookCopy bookCopy = loan.getBookCopy();
        bookCopy.bookCopyAvailable();
        bookCopyRepository.save(bookCopy);

        if (loan.getReturnDate().isAfter(loan.getDueDate())){
            fineService.createFine(loan);
        }

        loanRepository.save(loan);
        return new GetLoanDTO(loan);
    }


    @Transactional
    public GetLoanDTO renewLoan(Long id) {
        Loan loan = findLoan(id);
        verifyLoanBelongsUserLogged(loan);
        validatorsRenewLoan.forEach(l -> l.validate(loan));

        //Calcula os dias extras de acordo com o user Type
        int extraDays;
        if (loan.getUser().getUserType() == UserType.STUDENT){
            extraDays = 14;
        }else {
            extraDays = 30;
        }

        loan.renewLoan(extraDays);
        loanRepository.save(loan);
        return new GetLoanDTO(loan);
    }

    public Page<GetLoanDTO> getActiveLoans(Pageable pageable) {
        return loanRepository.findAllByLoanStatus(pageable ,LoanStatus.ACTIVE).map(GetLoanDTO::new);
    }

    public Page<GetLoanDTO> getLateLoans(Pageable pageable) {
        return loanRepository.findAllByLoanStatus(pageable ,LoanStatus.LATE).map(GetLoanDTO::new);
    }

    private Loan findLoan(Long id) {
        Optional<Loan> loan = loanRepository.findByIdAndEntitiesActive(id);

        if (loan.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Not Found or ");
        }

        return loan.get();
    }

    private void verifyLoanBelongsUserLogged (Loan loan) {
        Long userLoggedId = securityService.getLoggedUserId();

        if (!loan.getUser().getId().equals(userLoggedId)) {
            throw new IllegalArgumentException("Access denied. It is not possible to access loans from other users");
        }
    }

}
