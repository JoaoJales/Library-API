package br.com.Library_api.controller;

import br.com.Library_api.domain.report.ReportService;
import br.com.Library_api.dto.report.LateLoansRealTimeAdminDTO;
import br.com.Library_api.dto.report.TopBorrowedBooksDTO;
import br.com.Library_api.dto.report.TopUsersReadersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController (ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/books/top")
    public ResponseEntity<Page<TopBorrowedBooksDTO>> getTop10BorrowedBooks (@PageableDefault(size = 10) Pageable pageable) {
        Page<TopBorrowedBooksDTO> page = reportService.topBorrowedBooks(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/users/top")
    public ResponseEntity<Page<TopUsersReadersDTO>> getTopUsersReaders(@PageableDefault(size = 10) Pageable pageable) {
        Page<TopUsersReadersDTO> page = reportService.getTopReaders(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/loans/late")
    public ResponseEntity<Page<LateLoansRealTimeAdminDTO>> getLateLoansRealTime(@PageableDefault(size = 10) Pageable pageable) {
        Page<LateLoansRealTimeAdminDTO> page = reportService.getLateLoansRealTime(pageable);

        return ResponseEntity.ok().body(page);
    }
}
