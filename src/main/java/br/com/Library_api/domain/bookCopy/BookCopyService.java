package br.com.Library_api.domain.bookCopy;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.book.BookRepository;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.dto.bookCopy.BookCopyRegisterDTO;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.bookCopy.GetDetailingBookCopyDTO;
import br.com.Library_api.dto.bookCopy.PutBookCopyDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public BookCopyService (BookCopyRepository bookCopyRepository, BookRepository bookRepository, LoanRepository loanRepository){
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public BookCopy createBookCopy(@Valid BookCopyRegisterDTO data) {
        if (bookCopyRepository.existsByInventoryCode(data.inventoryCode())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The inventory code you entered already exists");
        }

        Optional<Book> book = bookRepository.findByIdAndActiveTrue(data.bookId());
        if (book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found or is not active.");
        }

        BookCopy bookCopy = new BookCopy(data, book.get());

        bookCopyRepository.save(bookCopy);
        return bookCopy;
    }

    public Page<GetBookCopyDTO> getBookCopies(Pageable pageable) {
        return bookCopyRepository.findAllByActiveTrue(pageable).map(GetBookCopyDTO::new);
    }

    public GetDetailingBookCopyDTO getDetalingBookCopy(Long id) {
        BookCopy bookCopy = findBookCopy(id);

        return new GetDetailingBookCopyDTO(bookCopy);
    }

    @Transactional
    public GetBookCopyDTO putBookCopy(@Valid PutBookCopyDTO data) {
        BookCopy bookCopy = findBookCopy(data.id());

        bookCopy.updateBookCopy(data);
        bookCopyRepository.save(bookCopy);

        return new GetBookCopyDTO(bookCopy);
    }

    @Transactional
    public void deleteBookCopy(Long id) {
        BookCopy bookCopy = findBookCopy(id);
        long activeLoans = loanRepository.countActiveLoansByBookCopy(bookCopy.getId());

        if (activeLoans > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot be deactivated while having active loans.");
        }

        bookCopy.deleteBookCopy();
        bookCopyRepository.save(bookCopy);
    }

    private BookCopy findBookCopy (Long id){
        Optional<BookCopy> bookCopy = bookCopyRepository.findByIdAndActiveTrue(id);

        if (bookCopy.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Copy Not Found or is not active");
        }

        return bookCopy.get();
    }

}
