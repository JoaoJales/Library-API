package br.com.Library_api.controller.user;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.author.AuthorService;
import br.com.Library_api.dto.author.AuthorRegisterDTO;
import br.com.Library_api.dto.author.GetAuthorSummaryDTO;
import br.com.Library_api.dto.author.GetDetailingAuthorDTO;
import br.com.Library_api.dto.author.PutAuthorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@SecurityRequirement(name = "bearer-key")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController (AuthorService authorService){
        this.authorService = authorService;
    }

    @Operation(summary = "Registrar Autor", tags = {"9 - Admin"})
    @PostMapping("/admin/authors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingAuthorDTO> postAuthor(@RequestBody @Valid AuthorRegisterDTO data, UriComponentsBuilder uriBuilder){
        Author author = authorService.createAuthor(data);

        var uri = uriBuilder.path("/authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingAuthorDTO(author));
    }

    @Operation(summary = "Consultar detalhes de um autor", tags = {"8 - Authors"})
    @GetMapping("/authors/{authorId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<GetDetailingAuthorDTO> getAuthor(@PathVariable Long authorId){
        GetDetailingAuthorDTO dto = authorService.getDetailingAuthor(authorId);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Consultar todos os Autores", tags = {"8 - Authors"})
    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')")
    public ResponseEntity<Page<GetAuthorSummaryDTO>> getAuthors(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<GetAuthorSummaryDTO> page = authorService.getAuthors(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Atualizar Autor", tags = {"9 - Admin"})
    @PutMapping("/admin/authors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetDetailingAuthorDTO> putAuthor(@RequestBody @Valid PutAuthorDTO data){
        GetDetailingAuthorDTO dto = authorService.putAuthor(data);

        return ResponseEntity.ok().body(dto);
    }
}
