package br.com.Library_api.domain.book;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.author.AuthorRepository;
import br.com.Library_api.dto.book.BookRegisterDTO;
import br.com.Library_api.dto.book.GetBooksDTO;
import br.com.Library_api.dto.book.GetDetailingBookDTO;
import br.com.Library_api.dto.book.PutBookDTO;
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

    public BookService (BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }

    @Transactional
    public Book createBook(BookRegisterDTO data) {
        if (bookRepository.existsByIsbn(data.isbn())){
            throw new IllegalArgumentException("Book with ISBN already registered");
        }

        Author author = authorRepository.findById(data.authorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
        Book book = new Book(data, author);
        author.getBooks().add(book);

        bookRepository.save(book);
        authorRepository.save(author);

        return book;
    }

    public Page<GetBooksDTO> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(GetBooksDTO::new);
    }

    public GetDetailingBookDTO getDetailingBook (Long id){
        var book = findBook(id);

        return new GetDetailingBookDTO(book);
    }

    @Transactional
    public GetDetailingBookDTO putBook(PutBookDTO data){
        var book = findBook(data.id());

        book.updateInfoBook(data);
        bookRepository.save(book);
        return new GetDetailingBookDTO(book);
    }

    private Book findBook(Long id){
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        return book.get();
    }

}

