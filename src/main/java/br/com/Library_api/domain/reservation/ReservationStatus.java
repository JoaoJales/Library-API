package br.com.Library_api.domain.reservation;

public enum ReservationStatus {
    ACTIVE,       // aguardando cópias
    FULFILLED,    // cópia disponível aguardando retirada
    COMPLETED,    // transformada em Loan
    EXPIRED,
    CANCELLED
}
