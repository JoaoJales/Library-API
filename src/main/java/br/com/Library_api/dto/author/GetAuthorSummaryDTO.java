package br.com.Library_api.dto.author;

import br.com.Library_api.domain.author.Author;

public record GetAuthorSummaryDTO(Long id, String name, String nationality) {
    public GetAuthorSummaryDTO(Author author){
        this(author.getId(), author.getName(), author.getNationality());
    }
}
