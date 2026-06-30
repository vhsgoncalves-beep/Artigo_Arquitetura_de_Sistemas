package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um livro do acervo da biblioteca.
 *
 * <p>O atributo {@code disponivel} controla se o livro pode ser
 * emprestado no momento, sendo atualizado pelas regras de negocio
 * de {@code EmprestimoService}.</p>
 */
@Entity
@Table(name = "livro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(unique = true)
    private String isbn;

    @Builder.Default
    @Column(nullable = false)
    private boolean disponivel = true;
}
