package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequestDTO;

import java.util.List;

/**
 * Interface pequena e coesa (ISP) com as operacoes relacionadas
 * exclusivamente a livros.
 */
public interface BookService {

    LivroDTO cadastrar(LivroRequestDTO request);

    List<LivroDTO> listarTodos();

    LivroDTO buscarPorId(Long id);
}
