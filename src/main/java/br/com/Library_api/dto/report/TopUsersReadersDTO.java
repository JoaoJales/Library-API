package br.com.Library_api.dto.report;

public record TopUsersReadersDTO(Long userId, String name, String email, Long totalLoans) {
}
