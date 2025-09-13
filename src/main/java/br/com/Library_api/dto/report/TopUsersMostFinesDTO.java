package br.com.Library_api.dto.report;

public record TopUsersMostFinesDTO(Long userId, String name, String email, Long totalFines) {
}
