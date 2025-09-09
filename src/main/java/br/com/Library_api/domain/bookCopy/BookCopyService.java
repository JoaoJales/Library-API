package br.com.Library_api.domain.bookCopy;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.book.BookRepository;
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

    public BookCopyService (BookCopyRepository bookCopyRepository, BookRepository bookRepository){
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookCopy createBookCopy(@Valid BookCopyRegisterDTO data) {
        if (bookCopyRepository.existsByInventoryCode(data.inventoryCode())){
            throw new IllegalArgumentException("The inventory code you entered already exists");
        }

        if (!bookRepository.existsById(data.bookId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found. The provided bookId does not exist");
        }

        Book book = bookRepository.findById(data.bookId()).get();
        BookCopy bookCopy = new BookCopy(data, book);

        book.getCopies().add(bookCopy); // não é precido salvar o book separadamente.

        bookCopyRepository.save(bookCopy);
        return bookCopy;
    }

    public Page<GetBookCopyDTO> getBookCopies(Pageable pageable) {
        return bookCopyRepository.findAll(pageable).map(GetBookCopyDTO::new);
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


    private BookCopy findBookCopy (Long id){
        Optional<BookCopy> bookCopy = bookCopyRepository.findById(id);

        if (bookCopy.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Copy Not Found");
        }

        return bookCopy.get();
    }

}
