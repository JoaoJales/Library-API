package br.com.Library_api.dto.fine;

import br.com.Library_api.domain.fine.Fine;
import br.com.Library_api.dto.loan.GetSummaryLoanDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetFineDTO (Long id, GetSummaryLoanDTO loan, BigDecimal amount, LocalDate issuedDate, Boolean paid){
    public GetFineDTO (Fine fine){
        this(
                fine.getId(),

                new GetSummaryLoanDTO(fine.getLoan().getId(), fine.getLoan().getUser().getName(),
                        fine.getLoan().getLoanDate(), fine.getLoan().getDueDate(), fine.getLoan().getReturnDate()),

                fine.getAmount(),
                fine.getIssuedDate(),
                fine.getPaid()
        );
    }
}
