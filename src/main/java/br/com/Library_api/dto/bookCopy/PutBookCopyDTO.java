package br.com.Library_api.dto.bookCopy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PutBookCopyDTO(
        @NotNull
        Long id,

        @NotBlank
        String inventoryCode
) {
}
