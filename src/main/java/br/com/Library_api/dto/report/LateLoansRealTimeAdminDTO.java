package br.com.Library_api.dto.report;

import java.time.LocalDate;

public record LateLoansRealTimeAdminDTO(Long loanId, String userName, String titleBook,
                                        String copy, LocalDate loanDate, LocalDate dueDate) {
}
