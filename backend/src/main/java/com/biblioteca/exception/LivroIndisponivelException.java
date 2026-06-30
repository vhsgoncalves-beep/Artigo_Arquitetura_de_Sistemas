package com.biblioteca.exception;

/**
 * Lancada ao tentar emprestar um livro que ja esta emprestado.
 */
public class LivroIndisponivelException extends RuntimeException {

    public LivroIndisponivelException(Long id) {
        super("Livro com id " + id + " nao esta disponivel para emprestimo");
    }
}
