package br.com.Library_api.domain.fine;

import br.com.Library_api.domain.loan.Loan;
import br.com.Library_api.dto.fine.GetFineDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class FineService {
    private final FineRepository fineRepository;

    public FineService (FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Transactional
    public Fine createFine (Loan loan) {
        Fine fine = new Fine(loan, calculateFine(loan));
        fineRepository.save(fine);

        return fine;
    }

    public GetFineDTO getFine(Long id) {
        Fine fine = findFine(id);

        return new GetFineDTO(fine);
    }

    @Transactional
    public GetFineDTO finePaid(Long id) {
        Fine fine = findFine(id);

        fine.finePaid();
        fineRepository.save(fine);

        return new GetFineDTO(fine);
    }

    private Fine findFine(Long id) {
        if (!fineRepository.existsById(id)){
            throw new EntityNotFoundException("Fine not found");
        }

        return fineRepository.findById(id).get();
    }

    private BigDecimal calculateFine(Loan loan) {
        LocalDate today = LocalDate.now();
        if (loan.getReturnDate() == null || !loan.getReturnDate().isAfter(loan.getDueDate())) {
            return BigDecimal.ZERO; // sem multa
        }

        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());

        BigDecimal dailyRate = switch (loan.getUser().getUserType()) {
            case STUDENT -> new BigDecimal("1.50");
            case PROFESSOR -> new BigDecimal("1.00");
            default -> BigDecimal.ZERO;
        };

        return dailyRate.multiply(BigDecimal.valueOf(daysLate));
    }
}
