package com.biblioteca.observer;

import com.biblioteca.factory.NotificationFactory;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.notification.NotificationType;
import org.springframework.stereotype.Component;

/**
 * Observador concreto que notifica o emprestimo via console,
 * delegando o envio efetivo para a abstracao {@code Notification}
 * obtida atraves da {@link NotificationFactory} (DIP).
 */
@Component
public class ConsoleObserver implements EmprestimoObserver {

    private final NotificationFactory notificationFactory;

    public ConsoleObserver(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void aoEmprestar(Emprestimo emprestimo) {
        String mensagem = "Emprestimo realizado: \"" + emprestimo.getLivro().getTitulo()
                + "\" para " + emprestimo.getNomeUsuario();
        notificationFactory.criar(NotificationType.CONSOLE)
                .enviar(emprestimo.getNomeUsuario(), mensagem);
    }
}
