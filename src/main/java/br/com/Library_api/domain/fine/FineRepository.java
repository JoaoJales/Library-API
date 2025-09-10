package br.com.Library_api.domain.fine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsByLoan_UserIdAndPaid(Long userId, Boolean paid);
}
