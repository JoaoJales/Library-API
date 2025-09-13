package br.com.Library_api.dto.report;

import br.com.Library_api.domain.book.Book;

public record TopBorrowedBooksDTO(Long bookId, String title, String authorName, Long loanCount) {
}
