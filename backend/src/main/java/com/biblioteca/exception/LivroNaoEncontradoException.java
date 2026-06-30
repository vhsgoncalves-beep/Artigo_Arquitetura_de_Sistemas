package com.biblioteca.exception;

/**
 * Lancada quando um livro consultado nao existe no acervo.
 */
public class LivroNaoEncontradoException extends RuntimeException {

    public LivroNaoEncontradoException(Long id) {
        super("Livro nao encontrado com id: " + id);
    }
}
