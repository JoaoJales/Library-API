package br.com.Library_api.domain.reservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.book.BookRepository;
import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.libraryPolicy.LibraryPolicyService;
import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanService;
import br.com.Library_api.domain.reservation.validations.createReservation.ValidatorCreateReservation;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserRepository;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.reservation.GetDetailingReservationDTO;
import br.com.Library_api.dto.reservation.GetLoanFromReservation;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.reservation.PostReservationDTO;
import br.com.Library_api.infra.security.SecurityService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final UserRepository userRepository;
    private final LoanService loanService;
    private final List<ValidatorCreateReservation> validatorsCreateReservation;
    private final SecurityService securityService;
    private final LibraryPolicyService libraryPolicyService;

    public ReservationService (ReservationRepository reservationRepository, BookRepository bookRepository,
                               BookCopyRepository bookCopyRepository, UserRepository userRepository,
                               LoanService loanService, List<ValidatorCreateReservation> validatorsCreateReservation,
                               SecurityService securityService, LibraryPolicyService libraryPolicyService) {
        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.userRepository = userRepository;
        this.loanService = loanService;
        this.validatorsCreateReservation = validatorsCreateReservation;
        this.securityService = securityService;
        this.libraryPolicyService = libraryPolicyService;
    }

    @Transactional
    public Reservation createReservation(@Valid PostReservationDTO data) {
        long loggedUserId = securityService.getLoggedUserId();
        User user = userRepository.findById(loggedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));


        Book book = bookRepository.findById(data.bookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        validatorsCreateReservation.forEach(r -> r.validate(user, book));

        Reservation reservation = new Reservation(user, book);
        reservationRepository.save(reservation);

        return reservation;
    }

    @Transactional
    public void processReservations(Book book) {
        List<Reservation> reservations = reservationRepository.findActiveReservationsByBookOrderByDate(book.getId());
        boolean available = bookCopyRepository.existsByBookIdAndAvailableAndActiveTrue(book.getId(), true);

        if (!reservations.isEmpty() && available){
            Reservation firstReservation = reservations.getFirst();
            firstReservation.fulfillReservation(libraryPolicyService.nextClosingTime(LocalDateTime.now().plusDays(2)));
            reservationRepository.save(firstReservation);

            // aqui poderia disparar um e-mail/notification
        }
    }

    @Transactional
    public GetLoanFromReservation pickUpReservedBook(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation Not Found"));

        if (!reservation.getReservationStatus().equals(ReservationStatus.FULFILLED)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation is not ready for pickup");
        }

        BookCopy bookCopy = bookCopyRepository.findFirstByBookIdAndAvailableTrueOrderByIdAsc(reservation.getBook().getId());
        Loan loan = loanService.createLoan(reservation, bookCopy);

        reservation.updateStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);

        return new GetLoanFromReservation(new GetLoanDTO(loan), new GetReservationSummaryDTO(reservation));
    }

    public GetDetailingReservationDTO getDetailingReservation (Long id) {
        Reservation reservation = findReservation(id);
        verifyReservationBelongsUserLogged(reservation);

        return new GetDetailingReservationDTO(reservation);
    }

    public Page<GetReservationSummaryDTO> getReservations (Pageable pageable) {
        Page<Reservation> page = reservationRepository.findAllActive(pageable);

        return page.map(GetReservationSummaryDTO::new);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        verifyReservationBelongsUserLogged(reservation);

        if (!(reservation.getReservationStatus().equals(ReservationStatus.ACTIVE) || reservation.getReservationStatus().equals(ReservationStatus.FULFILLED))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "it is not possible to cancel the reservation");
        }

        reservation.updateStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        processReservations(reservation.getBook());  // --> next to the queue
    }

    private Reservation findReservation (Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if (reservation.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation Not Found");
        }

        return reservation.get();
    }

    private void verifyReservationBelongsUserLogged (Reservation reservation) {
        Long userLoggedId = securityService.getLoggedUserId();

        if (!reservation.getUser().getId().equals(userLoggedId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN ,"Access denied. It is not possible to access reservation from other users");
        }

    }

}
