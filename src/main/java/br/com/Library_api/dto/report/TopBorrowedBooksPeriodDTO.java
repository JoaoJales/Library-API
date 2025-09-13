package br.com.Library_api.dto.report;

public record TopBorrowedBooksPeriodDTO(Long bookId, String title, String authorName, long borrowCount) {
}
