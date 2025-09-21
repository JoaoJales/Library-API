package br.com.Library_api.domain.loan;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.domain.libraryPolicy.LibraryPolicyService;
import br.com.Library_api.domain.loan.validations.createLoan.ValidatorCreateLoan;
import br.com.Library_api.domain.loan.validations.renewLoan.ValidatorRenewLoan;
import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import br.com.Library_api.infra.security.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;
    private final FineService fineService;
    private final List<ValidatorCreateLoan> validatorsCreateLoan;
    private final List<ValidatorRenewLoan> validatorsRenewLoan;
    private final SecurityService securityService;
    private final ReservationService reservationService;
    private final LibraryPolicyService libraryPolicyService;

    public LoanService (LoanRepository loanRepository, UserRepository userRepository,
                        BookCopyRepository bookCopyRepository, FineService fineService,
                        List<ValidatorCreateLoan> validatorsCreateLoan, List<ValidatorRenewLoan> validatorsRenewLoan,
                        SecurityService securityService, @Lazy final ReservationService reservationService,
                        LibraryPolicyService libraryPolicyService){


        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.fineService = fineService;
        this.validatorsCreateLoan = validatorsCreateLoan;
        this.validatorsRenewLoan = validatorsRenewLoan;
        this.securityService = securityService;
        this.reservationService = reservationService;
        this.libraryPolicyService = libraryPolicyService;
    }

    @Transactional
    public Loan createLoan(LoanRegisterDTO data) {
        BookCopy bookCopy = bookCopyRepository.findByInventoryCodeAndActiveTrue(data.bookCopyInventoryCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy Not Found or is not active."));

        User user = userRepository.findByIdAndActiveTrue(data.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found or is not active."));

        validatorsCreateLoan.forEach(v -> v.validate(data));

        Loan loan = new Loan(data, user ,bookCopy, calculatedueDate(user.getUserType(), LocalDateTime.now()));
        bookCopy.bookCopyNotAvailable();
        bookCopyRepository.save(bookCopy);

        loanRepository.save(loan);

        return loan;
    }

    @Transactional
    public Loan createLoan(Reservation reservation, BookCopy bookCopy) {
        LoanRegisterDTO data = new LoanRegisterDTO(reservation.getUser().getId(), bookCopy.getInventoryCode());
        validatorsCreateLoan.forEach(v -> v.validate(data));
        bookCopy.bookCopyNotAvailable();

        Loan loan = new Loan(data, reservation.getUser(), bookCopy, calculatedueDate(reservation.getUser().getUserType(), LocalDateTime.now()));

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

        loan.returnLoan();
        BookCopy bookCopy = loan.getBookCopy();
        bookCopy.bookCopyAvailable();
        bookCopyRepository.save(bookCopy);

        if (loan.getReturnDate().isAfter(loan.getDueDate())){
            fineService.createFine(loan);
        }

        loanRepository.save(loan);

        reservationService.processReservations(bookCopy.getBook());

        return new GetLoanDTO(loan);
    }


    @Transactional
    public GetLoanDTO renewLoan(Long id) {
        Loan loan = findLoan(id);
        verifyLoanBelongsUserLogged(loan);
        validatorsRenewLoan.forEach(l -> l.validate(loan));

        loan.renewLoan(calculatedueDate(loan.getUser().getUserType(), loan.getDueDate().atTime(0,0,0)));
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

    private LocalDate calculatedueDate(UserType userType, LocalDateTime date) {
        if (userType == UserType.STUDENT){
            return libraryPolicyService.nextOpeningTime(date.plusDays(14)).toLocalDate();
        }else {
            return libraryPolicyService.nextOpeningTime(date.plusDays(30)).toLocalDate();
        }
    }

}
