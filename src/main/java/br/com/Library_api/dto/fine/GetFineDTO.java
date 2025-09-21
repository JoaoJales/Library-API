package br.com.Library_api.dto.fine;

import br.com.Library_api.domain.fine.Fine;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetFineDTO (Long id, String username, BigDecimal amount, LocalDate issuedDate, Boolean paid, GetLoanSummaryDTO loan){
    public GetFineDTO (Fine fine){
        this(
                fine.getId(),
                fine.getLoan().getUser().getName(),


                fine.getAmount(),
                fine.getIssuedDate(),
                fine.getPaid(),
                new GetLoanSummaryDTO(fine.getLoan())
        );
    }
}
