package br.com.Library_api.domain.loan;

import br.com.Library_api.domain.bookCopy.BookCopy;
import br.com.Library_api.domain.user.User;
import br.com.Library_api.dto.loan.LoanRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(value = EnumType.STRING)
    private LoanStatus loanStatus;

    public Loan (LoanRegisterDTO data, User user, BookCopy bookCopy){
        this.bookCopy = bookCopy;
        this.user = user;
        this.loanDate = data.loanDate();
        this.dueDate = data.dueDate();
        this.returnDate = null;
        this.loanStatus = LoanStatus.ACTIVE;
    }

    public void returnLoan(){
        this.loanStatus = LoanStatus.RETURNED;
        this.returnDate = LocalDate.now();
    }

    public void lateLoan(){
        this.loanStatus = LoanStatus.LATE;
    }
}
