package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyService;
import br.com.Library_api.dto.bookCopy.BookCopyRegisterDTO;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.bookCopy.PutBookCopyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin/bookCopies")
@SecurityRequirement(name = "bearer-key")
public class AdminBookCopyController {
    private final BookCopyService bookCopyService;

    public AdminBookCopyController(BookCopyService bookCopyService){
        this.bookCopyService = bookCopyService;
    }

    @Operation(summary = "Registrar Cópia", tags = {"9 - Admin"})
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetBookCopyDTO> postBooks (@RequestBody @Valid BookCopyRegisterDTO data, UriComponentsBuilder uriBuilder) {
        BookCopy bookCopy = bookCopyService.createBookCopy(data);

        var uri = uriBuilder.path("/bookCopies/{id}").buildAndExpand(bookCopy.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetBookCopyDTO(bookCopy));
    }

    @Operation(summary = "Atualizar Cópia", tags = {"9 - Admin"})
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetBookCopyDTO> putBookCopy (@RequestBody @Valid PutBookCopyDTO data) {
        GetBookCopyDTO getBookCopyDTO = bookCopyService.putBookCopy(data);

        return ResponseEntity.ok().body(getBookCopyDTO);
    }

    @Operation(summary = "Deletar Cópia (soft delete)", tags = {"9 - Admin"})
    @DeleteMapping("/{bookCopyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteBookCopy (@PathVariable Long bookCopyId) {
        bookCopyService.deleteBookCopy(bookCopyId);

        return ResponseEntity.noContent().build();
    }
}
