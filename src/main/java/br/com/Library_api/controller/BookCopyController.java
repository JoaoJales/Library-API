package br.com.Library_api.controller;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.bookCopy.BookCopyService;
import br.com.Library_api.dto.bookCopy.BookCopyRegisterDTO;
import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import br.com.Library_api.dto.bookCopy.GetDetailingBookCopyDTO;
import br.com.Library_api.dto.bookCopy.PutBookCopyDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/bookCopies")
public class BookCopyController {
    private final BookCopyService bookCopyService;

    public BookCopyController (BookCopyService bookCopyService){
        this.bookCopyService = bookCopyService;
    }

    @PostMapping
    public ResponseEntity<GetBookCopyDTO> postBooks (@RequestBody @Valid BookCopyRegisterDTO data, UriComponentsBuilder uriBuilder) {
        BookCopy bookCopy = bookCopyService.createBookCopy(data);

        var uri = uriBuilder.path("bookCopies/{id}").buildAndExpand(bookCopy.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetBookCopyDTO(bookCopy));
    }

    @GetMapping
    public ResponseEntity<Page<GetBookCopyDTO>> getBooks (@PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<GetBookCopyDTO> page = bookCopyService.getBookCopies(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetDetailingBookCopyDTO> getDetailingBookCopy (@PathVariable Long id){
        GetDetailingBookCopyDTO data = bookCopyService.getDetalingBookCopy(id);

        return ResponseEntity.ok().body(data);
    }

    @PutMapping
    public ResponseEntity<GetBookCopyDTO> putBookCopy (@RequestBody @Valid PutBookCopyDTO data) {
        GetBookCopyDTO getBookCopyDTO = bookCopyService.putBookCopy(data);

        return ResponseEntity.ok().body(getBookCopyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBookCopy (@PathVariable Long id) {
        bookCopyService.deleteBookCopy(id);

        return ResponseEntity.noContent().build();
    }
}
