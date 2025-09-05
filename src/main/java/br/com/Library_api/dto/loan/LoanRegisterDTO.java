package br.com.Library_api.dto.loan;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record LoanRegisterDTO(
        @NotNull
        Long userId,

        @NotNull
        Long bookId,

        @NotNull
        @PastOrPresent
        LocalDate loanDate,

        @NotNull
        @Future
        LocalDate dueDate
) {
}
