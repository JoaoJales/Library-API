package br.com.Library_api.domain.report;

import br.com.Library_api.dto.report.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    private ReportRepositoryCustom reportRepositoryCustom;

    public ReportService (ReportRepositoryCustom reportRepository) {
        this.reportRepositoryCustom = reportRepository;
    }

    public Page<TopBorrowedBooksDTO> topBorrowedBooks(Pageable pageable) {
        return reportRepositoryCustom.findTopBooks(pageable);
    }

    public Page<TopUsersReadersDTO> getTopReaders(Pageable pageable) {
        return reportRepositoryCustom.findTopUsers(pageable);
    }

    public Page<TopMostReadAuthorsDTO> getTopMostReadAuthors(Pageable pageable) {
        return reportRepositoryCustom.findTopAuthors(pageable);
    }

    public Page<LateLoansRealTimeAdminDTO> getLateLoansRealTime(Pageable pageable) {
        return reportRepositoryCustom.findLateLoansRealTimeAdmin(pageable);
    }

    public Page<BookAvailabilityDTO> getBookAvailability(Pageable pageable) {
        return reportRepositoryCustom.findBookAvailability(pageable);
    }

    public Page<TopUsersMostFinesDTO> TopUsersMostFines(Pageable pageable) {
        return reportRepositoryCustom.findTopUsersMostFines(pageable);
    }

    public Page<UsersDebtorsDTO> getUsersDebtors (Pageable pageable) {
        return reportRepositoryCustom.findUsersDebtors(pageable);
    }

    public List<LoanStatsDTO> getLoansStatsByYear(Pageable pageable, int year) {
        return reportRepositoryCustom.findLoansStatsByYear(pageable, year);
    }

    public Page<TopBorrowedBooksPeriodDTO> getTopBorrowedBooksInPeriod(LocalDate start, LocalDate end, Pageable pageable) {
        return reportRepositoryCustom.findTopBorrowedBooksInPeriod(start, end, pageable);
    }
}
