package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;

/**
 * Interface do papel "Subject" no padrao Observer.
 * Define o contrato de registro e notificacao de observadores.
 */
public interface EmprestimoSubject {

    void adicionarObserver(EmprestimoObserver observer);

    void notificarObservers(Emprestimo emprestimo);
}
