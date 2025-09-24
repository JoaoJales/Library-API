package br.com.Library_api.controller.user;

import br.com.Library_api.domain.bookCopy.BookCopyService;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.bookCopy.GetDetailingBookCopyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/bookCopies")
@Tag(name = "4 - Book Copies")
@SecurityRequirement(name = "bearer-key")
public class BookCopyController {
    private final BookCopyService bookCopyService;

    public BookCopyController (BookCopyService bookCopyService){
        this.bookCopyService = bookCopyService;
    }

    @Operation(summary = "Consultar todos as Cópias de livros")
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<Page<GetBookCopyDTO>> getBookCopies (@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<GetBookCopyDTO> page = bookCopyService.getBookCopies(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar detalhes de uma Cópia")
    @GetMapping("{bookCopyId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<GetDetailingBookCopyDTO> getDetailingBookCopy (@PathVariable Long bookCopyId){
        GetDetailingBookCopyDTO data = bookCopyService.getDetalingBookCopy(bookCopyId);

        return ResponseEntity.ok().body(data);
    }

}
