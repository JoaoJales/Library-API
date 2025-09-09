package br.com.Library_api.dto.bookCopy;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.dto.book.GetBooksDTO;

public record GetDetailingBookCopyDTO(GetBookCopyDTO bookCopy, GetBooksDTO book) {
    public GetDetailingBookCopyDTO(BookCopy bookCopy){
        this(new GetBookCopyDTO(bookCopy), new GetBooksDTO(bookCopy.getBook()));
    }
}
