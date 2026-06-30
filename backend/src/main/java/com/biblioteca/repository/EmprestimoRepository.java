package com.biblioteca.repository;

import com.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para a entidade {@link Emprestimo}.
 */
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Busca todos os emprestimos ainda nao devolvidos
     * (dataDevolucao nula) de um determinado livro.
     */
    List<Emprestimo> findByLivroIdAndDataDevolucaoIsNull(Long livroId);
}
