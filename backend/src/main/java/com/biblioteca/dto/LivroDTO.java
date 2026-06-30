package com.biblioteca.dto;

import com.biblioteca.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para expor dados de {@link Livro} na API,
 * evitando acoplar o contrato externo a entidade JPA.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;

    public static LivroDTO fromEntity(Livro livro) {
        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autor(livro.getAutor())
                .isbn(livro.getIsbn())
                .disponivel(livro.isDisponivel())
                .build();
    }
}
