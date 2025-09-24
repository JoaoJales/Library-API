package br.com.Library_api.controller.user;

import br.com.Library_api.domain.book.BookService;
import br.com.Library_api.dto.book.BookCopiesResponseDTO;
import br.com.Library_api.dto.book.GetBooksDTO;
import br.com.Library_api.dto.book.GetDetailingBookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
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
@Tag(name = "3 - Books")
@SecurityRequirement(name = "bearer-key")
public class BookController {
    private final BookService bookService;

    public  BookController (BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "Consultar todos os livros")
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<Page<GetBooksDTO>> getBooks (@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<GetBooksDTO> page = bookService.getBooks(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar detalhes de um livro")
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> getDetailingBook (@PathVariable Long bookId){
        GetDetailingBookDTO detailingBook = bookService.getDetailingBook(bookId);

        return ResponseEntity.ok().body(detailingBook);
    }

    @Operation(summary = "Consultar Cópias de um livro")
    @GetMapping("/{bookId}/copies")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<BookCopiesResponseDTO> getBookCopies(@PageableDefault(size = 10, sort = "id") Pageable pageable, @PathVariable Long bookId){
        BookCopiesResponseDTO dto = bookService.getBookCopiesByBook(pageable, bookId);

        return ResponseEntity.ok().body(dto);
    }

}
