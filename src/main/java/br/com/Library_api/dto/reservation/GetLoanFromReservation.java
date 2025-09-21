package br.com.Library_api.dto.reservation;

import br.com.Library_api.dto.loan.GetLoanDTO;

public record GetLoanFromReservation(GetLoanDTO loan, GetReservationSummaryDTO reservation) {
}
