package br.com.Library_api.domain.report;

import br.com.Library_api.dto.report.LateLoansRealTimeAdminDTO;
import br.com.Library_api.dto.report.TopBorrowedBooksDTO;
import br.com.Library_api.dto.report.TopUsersReadersDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
}
