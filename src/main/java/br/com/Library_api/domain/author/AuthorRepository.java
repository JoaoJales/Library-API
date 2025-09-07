package br.com.Library_api.domain.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByNameAndBirthDate(String name, LocalDate birtDate);
}
