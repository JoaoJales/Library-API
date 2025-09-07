package br.com.Library_api.dto.loan;

import java.time.LocalDate;

public record GetSummaryLoanDTO(Long id, String username, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
}
