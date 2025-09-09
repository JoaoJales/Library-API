package br.com.Library_api.dto.bookCopy;

import br.com.Library_api.domain.bookCopy.BookCopy;

public record GetBookCopyDTO(Long id, String title, String inventoryCode, Boolean available) {
    public GetBookCopyDTO(BookCopy bookCopy){
        this(bookCopy.getId(), bookCopy.getBook().getTitle(), bookCopy.getInventoryCode(), bookCopy.getAvailable());
    }
}
