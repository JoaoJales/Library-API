package br.com.Library_api.dto.author;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public record GetDetailingAuthorDTO(Long authorId, String name, String nationality, LocalDate birthDate, List<Book> books) {
    public GetDetailingAuthorDTO(Author author){
        this(author.getId(), author.getName(), author.getNationality(), author.getBirthDate(), author.getBooks());

    }
}
