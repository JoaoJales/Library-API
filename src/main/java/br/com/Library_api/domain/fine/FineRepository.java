package br.com.Library_api.domain.fine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsByLoan_UserIdAndPaid(Long userId, Boolean paid);

    @Query("""
    SELECT f FROM Fine f
    WHERE f.loan.user.id = :userId
      AND f.paid = :bPaid
    """)
    Page<Fine> findFinesByUserIdAndPaidStatus(Pageable pageable, @Param("userId") Long userId, Boolean bPaid);
}
