package br.com.Library_api.domain.bookCopy;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByInventoryCode(String inventoryCode);

    Optional<BookCopy> findByInventoryCode(@NotBlank String s);
}
