package br.com.Library_api.domain.bookCopy;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.dto.bookCopy.BookCopyRegisterDTO;
import br.com.Library_api.dto.bookCopy.PutBookCopyDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    private String inventoryCode;

    private Boolean available;

    private boolean active;

    public BookCopy (BookCopyRegisterDTO data, Book book){
        this.book = book;
        this.inventoryCode = data.inventoryCode();
        this.available = true;
        this.active = true;
    }

    public void bookCopyNotAvailable(){
        this.available = false;
    }

    public void bookCopyAvailable(){
        this.available = true;
    }

    public void updateBookCopy(PutBookCopyDTO data){
        if (data.inventoryCode() != null) this.inventoryCode = data.inventoryCode();
    }

    public void deleteBookCopy(){
        this.active = false;
    }

}
