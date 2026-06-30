package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para o cadastro de um novo livro.
 * Mantem as validacoes de entrada isoladas do modelo de persistencia.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequestDTO {

    @NotBlank(message = "O titulo e obrigatorio")
    private String titulo;

    @NotBlank(message = "O autor e obrigatorio")
    private String autor;

    @NotBlank(message = "O ISBN e obrigatorio")
    private String isbn;
}
