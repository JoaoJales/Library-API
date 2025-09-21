package br.com.Library_api.controller;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.book.BookService;
import br.com.Library_api.dto.book.*;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public  BookController (BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> postBook(@RequestBody @Valid BookRegisterDTO data, UriComponentsBuilder uriBuilder){
        Book book = bookService.createBook(data);

        var uri = uriBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingBookDTO(book));
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

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> putBook(@RequestBody @Valid PutBookDTO data){
        var bookDto = bookService.putBook(data);

        return ResponseEntity.ok().body(bookDto);
    }

    @GetMapping("/{id}/copies")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<BookCopiesResponseDTO> getBookCopies(@PageableDefault(size = 10, sort = "id") Pageable pageable, @PathVariable Long id){
        BookCopiesResponseDTO dto = bookService.getBookCopiesByBook(pageable, id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/loans/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoansResponseDTO> getLoansByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id){
        var dto = bookService.getLoansByBook(pageable, id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/reservations/actives")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationsResponseDTO> getReservationsActivesOrFulfilledByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id){
        var dto = bookService.getReservationsActivesOrFulfilledByBook(pageable, id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/reservations/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsHistoryByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id){
        var page = bookService.getReservationsHistoryByBook(pageable, id);

        return ResponseEntity.ok().body(page);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteBook (@PathVariable Long id){
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
