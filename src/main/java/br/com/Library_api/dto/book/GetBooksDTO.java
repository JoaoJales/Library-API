package br.com.Library_api.dto.book;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.dto.author.GetAuthorSummaryDTO;

public record GetBooksDTO(Long id, String title, Integer publicationYear, String publisher, String genre, String isbn, GetAuthorSummaryDTO summaryAuthor) {
    public GetBooksDTO(Book book){
        this(book.getId(), book.getTitle(), book.getPublicationYear(), book.getPublisher(), book.getGenre(), book.getIsbn(), new GetAuthorSummaryDTO(book.getAuthor().getId(), book.getAuthor().getName()));
    }
}
