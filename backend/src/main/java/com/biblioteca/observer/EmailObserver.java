package com.biblioteca.observer;

import com.biblioteca.factory.NotificationFactory;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.notification.NotificationType;
import org.springframework.stereotype.Component;

/**
 * Observador concreto que notifica o emprestimo via e-mail (simulado),
 * delegando o envio efetivo para a abstracao {@code Notification}
 * obtida atraves da {@link NotificationFactory} (DIP).
 */
@Component
public class EmailObserver implements EmprestimoObserver {

    private final NotificationFactory notificationFactory;

    public EmailObserver(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    @Override
    public void aoEmprestar(Emprestimo emprestimo) {
        String mensagem = "Confirmamos o emprestimo do livro \"" + emprestimo.getLivro().getTitulo()
                + "\" em " + emprestimo.getDataEmprestimo();
        notificationFactory.criar(NotificationType.EMAIL)
                .enviar(emprestimo.getNomeUsuario(), mensagem);
    }
}
