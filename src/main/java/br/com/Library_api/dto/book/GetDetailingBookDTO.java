package br.com.Library_api.dto.book;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.dto.author.GetAuthorSummaryDTO;
import br.com.Library_api.dto.bookCopy.GetBookCopysDTO;

import java.util.List;

public record GetDetailingBookDTO(Long id, String title, Integer publicationYear, String publisher, String genre, String isbn, GetAuthorSummaryDTO summaryAuthor, List<GetBookCopysDTO> copies) {
    public GetDetailingBookDTO(Book book){
        this(book.getId(), book.getTitle(), book.getPublicationYear(), book.getPublisher(), book.getGenre(), book.getIsbn(),
                new GetAuthorSummaryDTO(book.getAuthor()),
                book.getCopies().stream().map(GetBookCopysDTO::new).toList()
        );
    }
}
