package br.com.Library_api.domain.book;

import br.com.Library_api.domain.author.Author;
import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.dto.book.BookRegisterDTO;
import br.com.Library_api.dto.book.PutBookDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookCopy> copies = new ArrayList<>();

    public Book (BookRegisterDTO data, Author author){
        this.title = data.title();
        this.publicationYear = data.publicationYear();
        this.publisher = data.publisher();
        this.genre = data.genre();
        this.isbn = data.isbn();
        this.author = author;
        this.active = true;
    }

    public void updateInfoBook(PutBookDTO data){
        if (data.title() != null) this.title = data.title();
        if (data.publicationYear() != null) this.publicationYear = data.publicationYear();
        if (data.publisher() != null) this.publisher = data.publisher();
        if (data.genre() != null) this.genre = data.genre();
    }

    public void deleteBook() {
        this.active = false;
    }
}
