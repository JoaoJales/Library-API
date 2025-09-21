package br.com.Library_api.dto.book;

import br.com.Library_api.dto.loan.GetLoanDTO;
import org.springframework.data.domain.Page;

public record LoansResponseDTO(LoansDataSummaryDTO summary, Page<GetLoanDTO> content) {

    public record LoansDataSummaryDTO(Long totalLoans){}
}
