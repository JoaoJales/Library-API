package br.com.Library_api.domain.scheduler;

import br.com.Library_api.domain.reservation.Reservation;
import br.com.Library_api.domain.reservation.ReservationRepository;
import br.com.Library_api.domain.reservation.ReservationService;
import br.com.Library_api.domain.reservation.ReservationStatus;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationSheduler {
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationSheduler (ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

//    @Scheduled(cron = "0 0 * * * *") // 1h  --> real rule
    @Scheduled(cron = "0 * * * * *")  //  1 minute --> test dev
    @Transactional
    public void expireReservations() {
        List<Reservation> expired = reservationRepository.findAllByReservationStatusAndExpiredAtBefore(ReservationStatus.FULFILLED, LocalDateTime.now());

        for (Reservation reservation : expired) {
            reservation.updateStatus(ReservationStatus.EXPIRED);
            reservationRepository.save(reservation);

            //next to the queue
            reservationService.processReservations(reservation.getBook());
        }
    }
}
