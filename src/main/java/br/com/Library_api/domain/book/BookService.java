package br.com.Library_api.domain.book;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.author.AuthorRepository;
import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyRepository;
import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationRepository;
import br.com.Library_api.domain.reservation.ReservationStatus;
import br.com.Library_api.dto.book.*;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.loan.GetLoanDTO;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookCopyRepository bookCopyRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;

    public BookService (BookRepository bookRepository, AuthorRepository authorRepository,
                        BookCopyRepository bookCopyRepository, LoanRepository loanRepository,
                        ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Book createBook(BookRegisterDTO data) {
        if (bookRepository.existsByIsbn(data.isbn())){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Book with ISBN already registered");
        }

        Author author = authorRepository.findById(data.authorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
        Book book = new Book(data, author);

        // não é preciso: authorRepository.save(author); e author.getBooks().add(book);

        bookRepository.save(book);

        return book;
    }

    public Page<GetBooksDTO> getBooks(Pageable pageable) {
        return bookRepository.findAllByActiveTrue(pageable).map(GetBooksDTO::new);
    }

    public GetDetailingBookDTO getDetailingBook (Long id){
        var book = findBook(id);

        return new GetDetailingBookDTO(book);
    }

    public Page<GetDetailingBookDTO> searchBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitle(title, pageable).map(GetDetailingBookDTO::new);
    }

    @Transactional
    public GetDetailingBookDTO putBook(PutBookDTO data){
        var book = findBook(data.bookId());

        book.updateInfoBook(data);
        bookRepository.save(book);
        return new GetDetailingBookDTO(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = findBook(id);

        long bookCopiesActives = bookCopyRepository.countAvailableBookCopiesByBook(book.getId());

        if (bookCopiesActives > 0){
            throw new IllegalStateException("The book cannot be deactivated while it has active copies.");
        }

        book.deleteBook();
        bookRepository.save(book);
    }

    private Book findBook(Long id){
        Optional<Book> book = bookRepository.findByIdAndActiveTrue(id);

        if (book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found or is not active");
        }

        return book.get();
    }

    public BookCopiesResponseDTO getBookCopiesByBook(Pageable pageable, Long id) {
        Page<BookCopy> copies = bookCopyRepository.findCopiesByBook(pageable, id);

        Long total = copies.getTotalElements();
        Long available = copies.stream().filter(BookCopy::getAvailable).count();
        Long borrowed = total - available;

        return new BookCopiesResponseDTO(
                new BookCopiesResponseDTO.BookCopiesSummaryDTO(total, available, borrowed),
                copies.map(GetBookCopyDTO::new)
        );
    }

    public LoansResponseDTO getLoansByBook(Pageable pageable, Long id) {
        Page<Loan> loans =  loanRepository.findLoansByBook(pageable, id);

        Long total = loans.getTotalElements();

        return new LoansResponseDTO(new LoansResponseDTO.LoansDataSummaryDTO(total), loans.map(GetLoanDTO::new));
    }

    public ReservationsResponseDTO getReservationsActivesOrFulfilledByBook (Pageable pageable, Long id) {
        Page<Reservation> reservations =  reservationRepository.findReservationsActivesOrFulfilledByBook(pageable, id);

        Long total = reservations.getTotalElements();
        Long actives = reservations.stream().filter(r -> r.getReservationStatus().equals(ReservationStatus.ACTIVE)).count();

        return new ReservationsResponseDTO(new ReservationsResponseDTO.ReservationDataSummaryDTO(total, actives), reservations.map(GetReservationSummaryDTO::new));
    }

    public Page<GetReservationSummaryDTO> getReservationsHistoryByBook (Pageable pageable, Long id) {
        return reservationRepository.findReservationsHistoryByBook(pageable, id).map(GetReservationSummaryDTO::new);
    }
}

