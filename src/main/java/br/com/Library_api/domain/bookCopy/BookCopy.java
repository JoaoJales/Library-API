package br.com.Library_api.domain.bookCopy;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.dto.bookCopy.BookCopyRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_copies")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String inventoryCode;

    private Boolean available;

    public BookCopy (BookCopyRegisterDTO data, Book book){
        this.book = book;
        this.inventoryCode = data.inventoryCode();
        this.available = true;
    }

    public void bookCopyNotAvailable(){
        this.available = false;
    }
}
