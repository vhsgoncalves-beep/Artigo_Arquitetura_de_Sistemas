package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para a entidade {@link Livro}.
 * O Spring Data gera a implementacao em tempo de execucao.
 */
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
