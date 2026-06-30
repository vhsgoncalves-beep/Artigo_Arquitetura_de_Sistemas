package com.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para realizar um emprestimo de livro.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoRequestDTO {

    @NotNull(message = "O id do livro e obrigatorio")
    private Long livroId;

    @NotBlank(message = "O nome do usuario e obrigatorio")
    private String nomeUsuario;
}
