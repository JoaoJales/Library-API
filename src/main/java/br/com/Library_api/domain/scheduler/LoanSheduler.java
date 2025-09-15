package br.com.Library_api.domain.scheduler;

import br.com.Library_api.domain.loan.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanSheduler {
    private final LoanRepository loanRepository;

    public LoanSheduler (LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

//    @Scheduled(cron = "0 0 0 * * *") // tod0 dia à meia noite (every day at midnight) - Production scheduler
    @Scheduled(cron = "0 * * * * *") // (every minute) - Dev Scheduler
    @Transactional
    public void updateLoans () {
        loanRepository.updateLoansToLate(LocalDate.now());
    }
}
