package br.com.Library_api.domain.bookCopy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    boolean existsByInventoryCode(String inventoryCode);
}
