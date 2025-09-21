package br.com.Library_api.controller.user;

import br.com.Library_api.domain.book.BookService;
import br.com.Library_api.dto.book.BookCopiesResponseDTO;
import br.com.Library_api.dto.book.GetBooksDTO;
import br.com.Library_api.dto.book.GetDetailingBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public  BookController (BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<Page<GetBooksDTO>> getBooks (@PageableDefault(size = 10, sort = "id") Pageable pageable){
       Page<GetBooksDTO> page = bookService.getBooks(pageable);

       return ResponseEntity.ok().body(page);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> getDetailingBook (@PathVariable Long id){
        GetDetailingBookDTO detailingBook = bookService.getDetailingBook(id);

        return ResponseEntity.ok().body(detailingBook);
    }

    @GetMapping("/{id}/copies")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<BookCopiesResponseDTO> getBookCopies(@PageableDefault(size = 10, sort = "id") Pageable pageable, @PathVariable Long id){
        BookCopiesResponseDTO dto = bookService.getBookCopiesByBook(pageable, id);

        return ResponseEntity.ok().body(dto);
    }

}
