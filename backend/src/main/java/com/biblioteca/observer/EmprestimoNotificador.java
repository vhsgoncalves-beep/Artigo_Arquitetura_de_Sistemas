package com.biblioteca.observer;

import com.biblioteca.model.Emprestimo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacao concreta do "Subject" do padrao Observer.
 *
 * <p>Mantem a lista de observadores ({@link EmprestimoObserver}) e os
 * notifica automaticamente sempre que um emprestimo ocorre. Os
 * observadores concretos (console, email) sao injetados pelo Spring,
 * permitindo adicionar novos tipos de observador sem alterar esta classe
 * (reforca o <b>OCP</b>).</p>
 */
@Component
public class EmprestimoNotificador implements EmprestimoSubject {

    private final List<EmprestimoObserver> observers = new ArrayList<>();

    /**
     * Construtor que recebe automaticamente, via Spring, todos os
     * beans que implementam {@link EmprestimoObserver} disponiveis
     * no contexto da aplicacao.
     */
    public EmprestimoNotificador(List<EmprestimoObserver> observersDisponiveis) {
        this.observers.addAll(observersDisponiveis);
    }

    @Override
    public void adicionarObserver(EmprestimoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notificarObservers(Emprestimo emprestimo) {
        for (EmprestimoObserver observer : observers) {
            observer.aoEmprestar(emprestimo);
        }
    }
}
