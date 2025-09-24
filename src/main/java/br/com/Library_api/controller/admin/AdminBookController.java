package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.book.BookService;
import br.com.Library_api.dto.book.*;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/admin/books")
@SecurityRequirement(name = "bearer-key")
public class AdminBookController {
    private final BookService bookService;

    public AdminBookController(BookService bookService){
        this.bookService = bookService;
    }

    @Operation(summary = "Registrar Livro", tags = {"9 - Admin"})
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> postBook(@RequestBody @Valid BookRegisterDTO data, UriComponentsBuilder uriBuilder){
        Book book = bookService.createBook(data);

        var uri = uriBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingBookDTO(book));
    }

    @Operation(summary = "Atualizar Livro", tags = {"9 - Admin"})
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingBookDTO> putBook(@RequestBody @Valid PutBookDTO data){
        var bookDto = bookService.putBook(data);

        return ResponseEntity.ok().body(bookDto);
    }

    @Operation(summary = "Consultar histórico de empréstimos de um livro", tags = {"9 - Admin"})
    @GetMapping("/{bookId}/loans/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoansResponseDTO> getLoansByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long bookId){
        var dto = bookService.getLoansByBook(pageable, bookId);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Consultar reservas ativas ou prontas de um livro", tags = {"9 - Admin"})
    @GetMapping("/{bookId}/reservations/actives")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationsResponseDTO> getReservationsActivesOrFulfilledByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long bookId){
        var dto = bookService.getReservationsActivesOrFulfilledByBook(pageable, bookId);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Consultar histórico de reservas de um livro", tags = {"9 - Admin"})
    @GetMapping("/{bookId}/reservations/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetReservationSummaryDTO>> getReservationsHistoryByBook (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long bookId){
        var page = bookService.getReservationsHistoryByBook(pageable, bookId);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Deletar livro (soft delete)", tags = {"9 - Admin"})
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteBook (@PathVariable Long bookId){
        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }
}
