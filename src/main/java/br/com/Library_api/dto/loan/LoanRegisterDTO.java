package br.com.Library_api.dto.loan;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record LoanRegisterDTO(
        @NotNull
        Long userId,

        @NotBlank
        @Pattern(regexp = "^[A-Z]{1,6}-[A-Z\\d]{1,6}-\\d{4}-\\d{3}$",
                message = "The inventory code must follow the format 'XXX-XXX-YYYY-NNN' (e.g., GGM-C-1967-001)," +
                        " where: 1–3 uppercase letters for the author initials, 1–3 uppercase letters for the title initials, a 4-digit year, and a 3-digit copy number.")
        String bookCopyInventoryCode,

        @NotNull
        @PastOrPresent
        LocalDate loanDate
) {
}
