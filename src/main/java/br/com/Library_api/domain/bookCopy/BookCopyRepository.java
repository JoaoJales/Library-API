package br.com.Library_api.domain.bookCopy;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByInventoryCode(String inventoryCode);

    Optional<BookCopy> findByInventoryCode(@NotBlank String s);

    @Query("SELECT bc FROM BookCopy bc WHERE bc.book.id = :id")
    Page<BookCopy> findCopiesByBook(Pageable pageable, Long id);
}
