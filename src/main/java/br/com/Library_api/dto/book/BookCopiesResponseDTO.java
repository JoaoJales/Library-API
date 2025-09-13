package br.com.Library_api.dto.book;

import br.com.Library_api.dto.bookCopy.GetBookCopyDTO;
import org.springframework.data.domain.Page;

public record BookCopiesResponseDTO(BookCopiesSummaryDTO summary, Page<GetBookCopyDTO> content) {

    public record BookCopiesSummaryDTO(Long totalCopies, Long availableCopies, Long borrowedCopies){}
}
