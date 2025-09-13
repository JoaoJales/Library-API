package br.com.Library_api.dto.report;

public record TopMostReadAuthorsDTO(Long id, String name, String nationality, Long totalBooksRegistered, Long totalLoans) {
}
