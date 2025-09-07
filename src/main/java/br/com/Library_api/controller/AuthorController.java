package br.com.Library_api.controller;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.author.AuthorService;
import br.com.Library_api.dto.author.AuthorRegisterDTO;
import br.com.Library_api.dto.author.GetAuthorSummaryDTO;
import br.com.Library_api.dto.author.GetDetailingAuthorDTO;
import br.com.Library_api.dto.author.PutAuthorDTO;
import br.com.Library_api.dto.user.GetDetailingUserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController (AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<GetDetailingAuthorDTO> postAuthor(@RequestBody @Valid AuthorRegisterDTO data, UriComponentsBuilder uriBuilder){
        Author author = authorService.createAuthor(data);

        var uri = uriBuilder.path("/authors/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingAuthorDTO(author));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetDetailingAuthorDTO> getAuthor(@PathVariable Long id){
        GetDetailingAuthorDTO dto = authorService.getDetailingAuthor(id);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<GetAuthorSummaryDTO>> getAuthors(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<GetAuthorSummaryDTO> page = authorService.getAuthors(pageable);

        return ResponseEntity.ok().body(page);
    }

    @PutMapping
    public ResponseEntity<GetDetailingAuthorDTO> putAuthor(@RequestBody @Valid PutAuthorDTO data){
        GetDetailingAuthorDTO dto = authorService.putAuthor(data);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping
    public ResponseEntity deleteAuthor(){
//        authorService.deleteAuthor();

        return ResponseEntity.noContent().build();
    }
}
