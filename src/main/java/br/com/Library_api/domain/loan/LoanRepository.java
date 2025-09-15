package br.com.Library_api.domain.loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.id = :userId AND l.loanStatus = 'ACTIVE' AND l.user.active = true")
    long countActiveLoansByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.bookCopy.id = :bookCopyId AND l.loanStatus = 'ACTIVE' AND l.bookCopy.active = true")
    long countActiveLoansByBookCopy(@Param("bookCopyId") Long bookCopyId);

    @Query("""
    SELECT
        CASE
            WHEN COUNT(l) > 0 THEN true
        ELSE false END
    FROM Loan l
    WHERE l.loanStatus = :loanStatus
     AND l.user.id = :id
     AND l.user.active = true
    """)
    boolean existsByLoanStatusAndUserIdAndUserActive(LoanStatus loanStatus, Long id);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.user.active = true
     AND l.bookCopy.active = true
     AND l.bookCopy.book.active = true
    """)
    Page<Loan> findAllByEntitiesActives(Pageable pageable);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.loanStatus = :loanStatus
      AND l.user.active = true
      AND l.bookCopy.active = true
      AND l.bookCopy.book.active = true
    """)
    Page<Loan> findAllByLoanStatus(Pageable pageable, @Param("loanStatus") LoanStatus loanStatus);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.user.id = :userId
      AND l.user.active = true
    ORDER BY l.loanDate DESC
    """)
    Page<Loan> findLoanHistoryByUser(Pageable pageable ,Long userId);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.user.id = :userId
      AND l.loanStatus = :status
      AND l.user.active = true
    ORDER BY l.loanDate DESC
    """)
    Page<Loan> findLoansByUserAndLoanStatus(Pageable pageable ,Long userId, LoanStatus status);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.bookCopy.book.id = :bookId
      AND l.bookCopy.book.active = true
      AND l.bookCopy.active = true
      AND l.user.active = true
    ORDER BY l.loanStatus
    """)
    Page<Loan> findLoansByBook(Pageable pageable, Long bookId);

    @Query("""
    SELECT l FROM Loan l
    WHERE l.id = :id
      AND l.user.active = true
      AND l.bookCopy.active = true
      AND l.bookCopy.book.active = true
    """)
    Optional<Loan> findByIdAndEntitiesActive(Long id);
}
