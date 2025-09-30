package br.com.Library_api.domain.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(@NotBlank String isbn);

    Page<Book> findAllByActiveTrue(Pageable pageable);

    Optional<Book> findByIdAndActiveTrue(Long id);

    @Query("""
    SELECT b
    FROM Book b
    WHERE b.title ILIKE CONCAT('%', :title, '%')
      AND b.active = true
    """)
    Page<Book> findByTitle(@Param("title") String title, Pageable pageable);
}
