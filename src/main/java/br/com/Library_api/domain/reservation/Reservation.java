package br.com.Library_api.domain.reservation;

import br.com.Library_api.domain.book.Book;
import br.com.Library_api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;


    public Reservation (User user, Book book){
        this.user = user;
        this.book = book;
        this.createdAt = LocalDateTime.now();
        this.reservationStatus = ReservationStatus.ACTIVE;
    }


    public void fulfillReservation(LocalDateTime expiredAt) {
        this.reservationStatus = ReservationStatus.FULFILLED;
        this.expiredAt = expiredAt;
    }

    public void updateStatus (ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

}
