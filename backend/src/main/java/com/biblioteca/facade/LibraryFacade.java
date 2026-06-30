package com.biblioteca.facade;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequestDTO;
import com.biblioteca.service.BookService;
import com.biblioteca.service.LoanService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Facade que simplifica e orquestra as operacoes de mais alto nivel
 * da biblioteca, escondendo do {@code Controller} a complexidade de
 * coordenar {@link BookService} e {@link LoanService}.
 *
 * <p>Responsabilidade unica (<b>SRP</b>): apenas orquestrar chamadas,
 * sem conter regra de negocio propria. Depende somente de abstracoes
 * (<b>DIP</b>).</p>
 */
@Component
public class LibraryFacade {

    private final BookService bookService;
    private final LoanService loanService;

    public LibraryFacade(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public LivroDTO cadastrarLivro(LivroRequestDTO request) {
        return bookService.cadastrar(request);
    }

    public List<LivroDTO> listarLivros() {
        return bookService.listarTodos();
    }

    /**
     * Orquestra o emprestimo de um livro. A notificacao automatica
     * (Observer) ocorre internamente dentro de {@link LoanService}.
     */
    public EmprestimoDTO borrowBook(EmprestimoRequestDTO request) {
        return loanService.emprestar(request);
    }

    public EmprestimoDTO returnBook(Long emprestimoId) {
        return loanService.devolver(emprestimoId);
    }

    public List<EmprestimoDTO> listarEmprestimos() {
        return loanService.listarTodos();
    }
}
