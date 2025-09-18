package br.com.Library_api.domain.fine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsByLoan_UserIdAndPaid(Long userId, Boolean paid);

    @Query("""
    SELECT f FROM Fine f
    WHERE f.loan.user.id = :userId
    AND f.paid = :bPaid
    AND f.loan.user.active = true
    """)
    Page<Fine> findFinesByUserIdAndPaidStatus(Pageable pageable, @Param("userId") Long userId, Boolean bPaid);

    @Query("""
    SELECT f FROM Fine f
    WHERE f.loan.user.active = true
    """)
    Page<Fine> findAllByUserActive(Pageable pageable);

    @Query("""
    SELECT f FROM Fine f
    WHERE f.id = :id
    AND f.loan.user.active = true
    """)
    Optional<Fine> findByIdAndRelatedUserActive(Long id);

    boolean existsByLoanId(Long id);
}
