package br.com.Library_api.domain.report;

import br.com.Library_api.dto.report.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public Page<TopBorrowedBooksDTO> findTopBooks(Pageable pageable) {
        String jpql = """
            SELECT new br.com.Library_api.dto.report.TopBorrowedBooksDTO(
                b.id, b.title, a.name, COUNT(l.id)
            )
            FROM Loan l
            JOIN l.bookCopy bc
            JOIN bc.book b
            JOIN b.author a
            GROUP BY b.id, b.title, a.name
            ORDER BY COUNT(l.id) DESC
        """;

        TypedQuery<TopBorrowedBooksDTO> query = em.createQuery(jpql, TopBorrowedBooksDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TopBorrowedBooksDTO> resultList = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT b.id) FROM Loan l JOIN l.bookCopy bc JOIN bc.book b", Long.class)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    public Page<TopUsersReadersDTO> findTopUsers(Pageable pageable) {
        String jpql = """
            SELECT new br.com.Library_api.dto.report.TopUsersReadersDTO(
                u.id, u.name, u.email, COUNT(l.id)
            )
            FROM Loan l
            JOIN l.user u
            GROUP BY u.id, u.name, u.email
            ORDER BY COUNT(l.id) DESC
        """;

        TypedQuery<TopUsersReadersDTO> query = em.createQuery(jpql, TopUsersReadersDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TopUsersReadersDTO> resultList = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT u.id) FROM Loan l JOIN l.user u", Long.class)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }


    public Page<TopMostReadAuthorsDTO> findTopAuthors(Pageable pageable) {
        String jpql = """
                SELECT new br.com.Library_api.dto.report.TopMostReadAuthorsDTO(
                    a.id, a.name, a.nationality,
                    (SELECT COUNT(DISTINCT b2.id) FROM Book b2 WHERE b2.author = a),
                    COUNT(l.id)
                )
                
                FROM Loan l
                JOIN l.bookCopy bc
                JOIN bc.book b
                JOIN b.author a
                GROUP BY a.id, a.name, a.nationality
                ORDER BY COUNT(l.id) DESC
                """;

        TypedQuery<TopMostReadAuthorsDTO> query = em.createQuery(jpql, TopMostReadAuthorsDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TopMostReadAuthorsDTO> list = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT a.id) FROM Loan l JOIN l.bookCopy bc JOIN bc.book b JOIN b.author a", Long.class)
                .getSingleResult();

        return new PageImpl<>(list, pageable, total);
    }

    public Page<LateLoansRealTimeAdminDTO> findLateLoansRealTimeAdmin(Pageable pageable) {
        String jpql = """
            SELECT new br.com.Library_api.dto.report.LateLoansRealTimeAdminDTO(
                l.id, u.name, b.title, bc.inventoryCode, l.loanDate, l.dueDate
            )
            FROM Loan l
            JOIN l.user u
            JOIN l.bookCopy bc
            JOIN bc.book b
            WHERE l.returnDate IS NULL AND l.dueDate < CURRENT_DATE
            ORDER BY l.dueDate ASC
        """;

        TypedQuery<LateLoansRealTimeAdminDTO> query = em.createQuery(jpql, LateLoansRealTimeAdminDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<LateLoansRealTimeAdminDTO> resultList = query.getResultList();
        long total = em.createQuery("SELECT COUNT(l.id) FROM Loan l WHERE l.returnDate IS NULL AND l.dueDate < CURRENT_DATE", Long.class)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    public Page<BookAvailabilityDTO> findBookAvailability(Pageable pageable) {
        String jpql = """
                SELECT new br.com.Library_api.dto.report.BookAvailabilityDTO(
                    b.id, b.title, COUNT(bc.id)
                )

                FROM BookCopy bc
                JOIN bc.book b
                GROUP BY b.id, b.title
                ORDER BY COUNT(bc.id) ASC
                """;

        TypedQuery<BookAvailabilityDTO> query = em.createQuery(jpql, BookAvailabilityDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<BookAvailabilityDTO> resultList = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT b.id) FROM BookCopy bc JOIN bc.book b", Long.class)
                .getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    public Page<TopUsersMostFinesDTO> findTopUsersMostFines(Pageable pageable) {
        String jpql = """
                SELECT new br.com.Library_api.dto.report.TopUsersMostFinesDTO(
                    u.id, u.name, u.email, COUNT(f.id)
                )

                FROM Fine f
                JOIN f.loan l
                JOIN l.user u
                GROUP BY u.id, u.name, u.email
                ORDER BY COUNT(f.id) DESC
                """;

        TypedQuery<TopUsersMostFinesDTO> query = em.createQuery(jpql, TopUsersMostFinesDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TopUsersMostFinesDTO> list = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT u.id) FROM Loan l JOIN l.user u", Long.class)
                .getSingleResult();

        return new PageImpl<>(list, pageable, total);
    }

    public Page<UsersDebtorsDTO> findUsersDebtors(Pageable pageable) {
        String jpql = """
                SELECT new br.com.Library_api.dto.report.UsersDebtorsDTO(
                    u.id, u.name, u.email, u.userType, COUNT(f.id)
                )
                FROM Fine f
                JOIN f.loan l
                JOIN l.user u
                WHERE f.paid = false
                GROUP BY u.id, u.name, u.email, u.userType
                ORDER BY COUNT(f.id) DESC
                """;

        TypedQuery<UsersDebtorsDTO> query = em.createQuery(jpql, UsersDebtorsDTO.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<UsersDebtorsDTO> list = query.getResultList();
        long total = em.createQuery("SELECT COUNT(DISTINCT u.id) FROM Fine f JOIN f.loan l JOIN l.user u WHERE f.paid = false", Long.class)
                .getSingleResult();

        return new PageImpl<>(list, pageable, total);
    }


    public List<LoanStatsDTO> findLoansStatsByYear(Pageable pageable, int year) {
        String jpql = """
            SELECT new br.com.Library_api.dto.report.LoanStatsDTO(
                MONTH(l.loanDate), COUNT(l.id)
            )
            FROM Loan l
            WHERE YEAR(l.loanDate) = :year
            GROUP BY MONTH(l.loanDate)
            ORDER BY MONTH(l.loanDate)
        """;

        return em.createQuery(jpql, LoanStatsDTO.class)
                .setParameter("year", year)
                .getResultList();
    }

    public Page<TopBorrowedBooksPeriodDTO> findTopBorrowedBooksInPeriod(LocalDate start, LocalDate end, Pageable pageable) {
        String jpql = """
            SELECT new br.com.Library_api.dto.report.TopBorrowedBooksPeriodDTO(
                b.id, b.title, a.name, COUNT(l.id)
            )
            FROM Loan l
            JOIN l.bookCopy bc
            JOIN bc.book b
            JOIN b.author a
            WHERE l.loanDate BETWEEN :start AND :end
            GROUP BY b.id, b.title, a.name
            ORDER BY COUNT(l.id) DESC
        """;

        TypedQuery<TopBorrowedBooksPeriodDTO> query = em.createQuery(jpql, TopBorrowedBooksPeriodDTO.class)
                .setParameter("start", start)
                .setParameter("end", end);

        // paginação
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<TopBorrowedBooksPeriodDTO> result = query.getResultList();

        Long total = em.createQuery("""
            SELECT COUNT(DISTINCT b.id)
            FROM Loan l
            JOIN l.bookCopy bc
            JOIN bc.book b
            WHERE l.loanDate BETWEEN :start AND :end
        """, Long.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();

        return new PageImpl<>(result, pageable, total);
    }
}
