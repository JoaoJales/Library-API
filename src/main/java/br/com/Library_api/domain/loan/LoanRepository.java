package br.com.Library_api.domain.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT COUNT(*) FROM Loan l WHERE l.user.id = :userId AND l.loanStatus = 'ACTIVE'")
    long countActiveLoansByUser(@Param("userId") Long userId);

    boolean existsByLoanStatusAndUserId(LoanStatus loanStatus, Long id);

    Page<Loan> findAllByLoanStatus(Pageable pageable, LoanStatus loanStatus);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId ORDER BY l.loanDate DESC")
    Page<Loan> findLoanHistoryByUser(Pageable pageable ,Long userId);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.loanStatus = :status ORDER BY l.loanDate DESC")
    Page<Loan> findLoansByUserAndLoanStatus(Pageable pageable ,Long userId, LoanStatus status);

    @Query("SELECT l FROM Loan l WHERE l.bookCopy.book.id = :bookId")
    Page<Loan> findLoansByBook(Pageable pageable, Long bookId);
}
