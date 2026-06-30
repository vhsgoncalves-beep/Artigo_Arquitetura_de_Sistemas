package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;

/**
 * Interface do padrao Observer (papel "Observer").
 * Define o contrato para quem deseja ser notificado quando
 * um emprestimo ocorre.
 */
public interface EmprestimoObserver {

    /**
     * Chamado automaticamente quando um novo emprestimo e realizado.
     *
     * @param emprestimo emprestimo recem-criado
     */
    void aoEmprestar(Emprestimo emprestimo);
}
