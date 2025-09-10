package br.com.Library_api.domain.loan;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.fine.FineRepository;
import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;
    private final FineService fineService;
    private final FineRepository fineRepository;

    public LoanService (LoanRepository loanRepository, UserRepository userRepository, BookCopyRepository bookCopyRepository, FineService fineService, FineRepository fineRepository){
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.fineService = fineService;
        this.fineRepository = fineRepository;
    }

    @Transactional
    public Loan createLoan(LoanRegisterDTO data) {
        if (!userRepository.existsById(data.userId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found. The provided userId does not exist");
        }

        BookCopy bookCopy = bookCopyRepository.findByInventoryCode(data.bookCopyInventoryCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book copy Not Found. The provided bookCopy InventoryCode does not exist"));

        User user = userRepository.findById(data.userId()).get();

        if (loanRepository.existsByLoanStatusAndUserId(LoanStatus.LATE, user.getId())){
            throw new IllegalStateException("This user has an overdue loan. Please return the book and pay the fine to be eligible for a new loan.");
        }

        if (fineRepository.existsByLoan_UserIdAndPaid(data.userId(), false)){
            throw new IllegalStateException("This user has an outstanding fine. Please pay your fine to be eligible for a new loan.");
        }

        if (!bookCopy.getAvailable()){
            throw new IllegalStateException("This book copy is not available.");
        }

        if (user.getUserType() == UserType.VISITOR){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Visitors are not allowed to take out loans.");
        }

        //Verifica a quantidade de Loans feito pelo usuário
        long activeLoans = loanRepository.countActiveLoansByUser(data.userId());
        if (user.getUserType() == UserType.STUDENT && activeLoans >= 3) {
            throw new IllegalStateException("Student cannot have more than 3 active loans.");
        }

        if (user.getUserType() == UserType.PROFESSOR && activeLoans >= 5) {
            throw new IllegalStateException("Professor cannot have more than 5 active loans.");
        }

        Loan loan = new Loan(data, user ,bookCopy);
        loanRepository.save(loan);

        bookCopy.bookCopyNotAvailable();
        bookCopyRepository.save(bookCopy);

        return loan;
    }

    public Page<GetLoanDTO> getLoans(Pageable pageable) {
        return loanRepository.findAll(pageable).map(GetLoanDTO::new);
    }


    public GetLoanDTO getDetalingLoan(Long id) {
        Loan loan = findLoan(id);

        return new GetLoanDTO(loan);
    }

    @Transactional
    public GetLoanDTO returnLoan (Long id){
        Loan loan = findLoan(id);
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
    public GetLoanDTO setlateLoan(Long id) {
        Loan loan = findLoan(id);
        loan.lateLoan();

        return new GetLoanDTO(loan);
    }

    @Transactional
    public GetLoanDTO renewLoan(Long id) {
        Loan loan = findLoan(id);

        if (loan.getLoanStatus() != LoanStatus.ACTIVE) {
            throw new IllegalStateException("Only active loans can be renewed.");
        }

        if (!loan.getDueDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Cannot renew on or after due date.");
        }

        if (loan.getRenewals() >= 2) {
            throw new IllegalStateException("Maximum number of renewals reached.");
        }

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


    private Loan findLoan(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);

        if (loan.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan Not Found");
        }

        return loan.get();
    }
}
