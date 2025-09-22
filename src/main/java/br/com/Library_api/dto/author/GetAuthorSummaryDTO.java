package br.com.Library_api.dto.author;

import br.com.Library_api.domain.author.Author;

public record GetAuthorSummaryDTO(Long authorId, String name, String nationality) {
    public GetAuthorSummaryDTO(Author author){
        this(author.getId(), author.getName(), author.getNationality());
    }
}
