package br.com.Library_api.controller.report;

import br.com.Library_api.domain.report.ReportService;
import br.com.Library_api.dto.report.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/authors/top")
    public ResponseEntity<Page<TopMostReadAuthorsDTO>> getTopMostReadAuthors(@PageableDefault(size = 10) Pageable pageable) {
        Page<TopMostReadAuthorsDTO> page = reportService.getTopMostReadAuthors(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/loans/late")
    public ResponseEntity<Page<LateLoansRealTimeAdminDTO>> getLateLoansRealTime(@PageableDefault(size = 10) Pageable pageable) {
        Page<LateLoansRealTimeAdminDTO> page = reportService.getLateLoansRealTime(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/books/availability")
    public ResponseEntity<Page<BookAvailabilityDTO>> getBookAvailability (@PageableDefault(size = 10) Pageable pageable){
        Page<BookAvailabilityDTO> page = reportService.getBookAvailability(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/users/fines/top")
    public ResponseEntity<Page<TopUsersMostFinesDTO>> getTopUsersMostFines (@PageableDefault(size = 10) Pageable pageable){
        Page<TopUsersMostFinesDTO> page = reportService.topUsersMostFines(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/users/debtors")
    public ResponseEntity<Page<UsersDebtorsDTO>> getUsersDebtors (@PageableDefault(size = 10) Pageable pageable){
        Page<UsersDebtorsDTO> page = reportService.getUsersDebtors(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/loans/stats")
    public ResponseEntity<List<LoanStatsDTO>> getLoansStats (@PageableDefault(size = 10) Pageable pageable, @RequestParam int year) {
        List<LoanStatsDTO> list = reportService.getLoansStatsByYear(pageable, year);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/books/top/period")
    public ResponseEntity<Page<TopBorrowedBooksPeriodDTO>> getTopBorrowedBooksInPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                                                       @PageableDefault(size = 10) Pageable pageable) {

        Page<TopBorrowedBooksPeriodDTO> page = reportService.getTopBorrowedBooksInPeriod(start, end, pageable);

        return ResponseEntity.ok().body(page);
    }

}
