package br.com.Library_api.domain.author;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.dto.author.AuthorRegisterDTO;
import br.com.Library_api.dto.author.PutAuthorDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    public Author (AuthorRegisterDTO data){
        this.name = data.name();
        this.nationality = data.nationality();
        this.birthDate = data.birthDate();
    }

    public void updateInfoAuthor(PutAuthorDTO data){
        if (data.name() != null) this.name = data.name();
        if (data.nationality() != null) this.nationality = data.nationality();
        if (data.birthDate() != null) this.birthDate = data.birthDate();
    }

}
