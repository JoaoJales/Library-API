package br.com.Library_api.dto.fine;

import br.com.Library_api.domain.fine.Fine;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetFineDTO (Long id, GetLoanSummaryDTO loan, BigDecimal amount, LocalDate issuedDate, Boolean paid){
    public GetFineDTO (Fine fine){
        this(
                fine.getId(),

                new GetLoanSummaryDTO(fine.getLoan()),

                fine.getAmount(),
                fine.getIssuedDate(),
                fine.getPaid()
        );
    }
}
