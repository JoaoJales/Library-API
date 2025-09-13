package br.com.Library_api.domain.report;

import br.com.Library_api.dto.report.LateLoansRealTimeAdminDTO;
import br.com.Library_api.dto.report.TopBorrowedBooksDTO;
import br.com.Library_api.dto.report.TopUsersReadersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<LateLoansRealTimeAdminDTO> getLateLoansRealTime(Pageable pageable) {
        return reportRepositoryCustom.findLateLoansRealTimeAdmin(pageable);
    }
}
