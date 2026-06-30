package com.biblioteca.service;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.dto.EmprestimoRequestDTO;

import java.util.List;

/**
 * Interface pequena e coesa (ISP) com as operacoes relacionadas
 * exclusivamente a emprestimos.
 */
public interface LoanService {

    EmprestimoDTO emprestar(EmprestimoRequestDTO request);

    EmprestimoDTO devolver(Long emprestimoId);

    List<EmprestimoDTO> listarTodos();
}
