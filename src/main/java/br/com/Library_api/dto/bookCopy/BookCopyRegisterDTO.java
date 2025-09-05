package br.com.Library_api.dto.bookCopy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCopyRegisterDTO(
        @NotNull
        Long bookId,

        @NotBlank
        String inventoryCode
) {
}
