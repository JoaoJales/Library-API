package br.com.Library_api.domain.author;

import br.com.Library_api.dto.author.AuthorRegisterDTO;
import br.com.Library_api.dto.author.GetAuthorSummaryDTO;
import br.com.Library_api.dto.author.GetDetailingAuthorDTO;
import br.com.Library_api.dto.author.PutAuthorDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService (AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(@Valid AuthorRegisterDTO data) {
        if(authorRepository.existsByNameAndBirthDate(data.name(), data.birthDate())){
            throw new ResponseStatusException(HttpStatus.CONFLICT , "Author with name and date of birth already registered");
        }

        Author author = new Author(data);
        authorRepository.save(author);
        return author;
    }

    public GetDetailingAuthorDTO getDetailingAuthor(Long id) {
        Author author = findAuthor(id);

        return new GetDetailingAuthorDTO(author);
    }


    public Page<GetAuthorSummaryDTO> getAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable).map(GetAuthorSummaryDTO::new);
    }

    public GetDetailingAuthorDTO putAuthor(PutAuthorDTO data) {
        Author author = findAuthor(data.id());
        author.updateInfoAuthor(data);

        authorRepository.save(author);

        return new GetDetailingAuthorDTO(author);
    }

    private Author findAuthor(Long id){
        var author = authorRepository.findById(id);

        if (author.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        return author.get();
    }
}
