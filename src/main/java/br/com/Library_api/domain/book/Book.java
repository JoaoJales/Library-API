package br.com.Library_api.domain.book;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.dto.book.BookRegisterDTO;
import br.com.Library_api.dto.book.PutBookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer publicationYear;

    private String publisher;

    private String genre;

    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Book (BookRegisterDTO data, Author author){
        this.title = data.title();
        this.publicationYear = data.publicationYear();
        this.publisher = data.publisher();
        this.genre = data.genre();
        this.isbn = data.isbn();
        this.author = author;
    }

    public void updateInfoBook(PutBookDTO data){
        if (data.title() != null) this.title = data.title();
        if (data.publicationYear() != null) this.publicationYear = data.publicationYear();
        if (data.publisher() != null) this.publisher = data.publisher();
        if (data.genre() != null) this.genre = data.genre();
    }
}
