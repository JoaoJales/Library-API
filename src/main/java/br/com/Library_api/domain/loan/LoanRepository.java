package br.com.Library_api.domain.loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT COUNT(*) FROM Loan l WHERE l.user.id = :userId AND l.loanStatus = 'ACTIVE'")
    long countActiveLoansByUser(@Param("userId") Long userId);

    boolean existsByLoanStatusAndUserId(LoanStatus loanStatus, Long id);
}
