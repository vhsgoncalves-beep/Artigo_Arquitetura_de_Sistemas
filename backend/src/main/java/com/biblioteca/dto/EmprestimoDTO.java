package com.biblioteca.dto;

import com.biblioteca.model.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO utilizado para expor dados de {@link Emprestimo} na API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    private Long id;
    private Long livroId;
    private String tituloLivro;
    private String nomeUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public static EmprestimoDTO fromEntity(Emprestimo emprestimo) {
        return EmprestimoDTO.builder()
                .id(emprestimo.getId())
                .livroId(emprestimo.getLivro().getId())
                .tituloLivro(emprestimo.getLivro().getTitulo())
                .nomeUsuario(emprestimo.getNomeUsuario())
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucao(emprestimo.getDataDevolucao())
                .build();
    }
}
