package br.com.Library_api.domain.fine;

import br.com.Library_api.domain.loan.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fine {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private BigDecimal amount;

    private LocalDate issuedDate;

    private Boolean paid;

    public void finePaid(){
        this.paid = true;
    }
}
