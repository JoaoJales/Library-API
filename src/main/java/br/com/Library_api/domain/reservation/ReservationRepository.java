package br.com.Library_api.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
            SELECT COUNT(r.id) FROM Reservation r
            WHERE r.user.id = :userId
             AND (r.reservationStatus = 'ACTIVE' OR r.reservationStatus = 'FULFILLED')
            """)
    long countReservationsByUser(Long userId);

    @Query("""
            SELECT COUNT(r.id) FROM Reservation r
            WHERE r.book.id = :bookId
             AND r.reservationStatus = 'ACTIVE'
             OR r.reservationStatus = 'FULFILLED'
            """)
    long countReservationsByBook(Long bookId);

    @Query(""" 
            SELECT 
                CASE WHEN COUNT(r) > 0 
                    THEN true 
                    ELSE false 
                END
            FROM Reservation r
            WHERE r.user.id = :userId 
            AND r.book.id = :bookId 
            AND r.reservationStatus = 'ACTIVE'
            """)
    boolean existsActiveReservationByUserAndBook(Long userId, Long bookId);

    @Query("""
            SELECT r FROM Reservation r
            JOIN r.book b
            WHERE b.id = :bookId
            AND r.reservationStatus = 'ACTIVE'
            ORDER BY r.createdAt ASC
            """)
    List<Reservation> findActiveReservationsByBookOrderByDate(@Param("bookId") Long bookId);

    List<Reservation> findAllByReservationStatusAndExpiredAtBefore(ReservationStatus reservationStatus, LocalDateTime now);

    @Query("""
            SELECT r FROM Reservation r
            JOIN r.book b
            JOIN r.user u
            WHERE b.active = true
            AND u.active = true
            ORDER BY r.createdAt DESC
            """)
    Page<Reservation> findAllActive(Pageable pageable);

    boolean existsByBookIdAndReservationStatus(Long id, ReservationStatus reservationStatus);

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.user.id = :userId AND r.user.active = true
            ORDER BY r.createdAt DESC
            """)
    Page<Reservation> findReservationHistoryByUser(Pageable pageable, Long userId);

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.book.id = :bookId AND r.book.active = true
            ORDER BY r.createdAt DESC
            """)
    Page<Reservation> findReservationsHistoryByBook(Pageable pageable, Long bookId);

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.user.id = :userId AND r.user.active = true
            AND (r.reservationStatus = 'ACTIVE' OR r.reservationStatus = 'FULFILLED')
            ORDER BY r.createdAt DESC
            """)
    Page<Reservation> findReservationsActivesOrFulfilledByUser(Pageable pageable, Long userId);


    @Query("""
        SELECT r FROM Reservation r
        WHERE r.book.id = :bookId
         AND r.user.active = true
         AND r.book.active = true
         AND (r.reservationStatus = 'ACTIVE' OR r.reservationStatus = 'FULFILLED')
        ORDER BY r.reservationStatus DESC
    """)
    Page<Reservation> findReservationsActivesOrFulfilledByBook(Pageable pageable, Long bookId);

}
