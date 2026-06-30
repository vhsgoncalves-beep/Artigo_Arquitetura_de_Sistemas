package com.biblioteca.factory;

import com.biblioteca.notification.ConsoleNotification;
import com.biblioteca.notification.EmailNotification;
import com.biblioteca.notification.Notification;
import com.biblioteca.notification.NotificationType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Factory Method responsavel por decidir, em tempo de execucao,
 * qual implementacao de {@link Notification} deve ser utilizada.
 *
 * <p>Aplica o principio <b>OCP</b>: para suportar um novo tipo de
 * notificacao (ex.: SMS), basta criar uma nova implementacao de
 * {@link Notification}, registra-la como {@code @Component} e
 * adiciona-la ao mapa de tipos — sem alterar nenhuma classe
 * de regra de negocio existente.</p>
 *
 * <p>As implementacoes concretas sao injetadas pelo Spring (DIP),
 * eliminando o uso de {@code new} dentro da fabrica.</p>
 */
@Component
public class NotificationFactory {

    private final Map<NotificationType, Notification> implementacoes;

    public NotificationFactory(
            EmailNotification emailNotification,
            ConsoleNotification consoleNotification) {
        this.implementacoes = Map.of(
                NotificationType.EMAIL, emailNotification,
                NotificationType.CONSOLE, consoleNotification
        );
    }

    /**
     * Cria (resolve) a implementacao de {@link Notification}
     * correspondente ao tipo informado.
     *
     * @param tipo tipo de notificacao desejado
     * @return implementacao concreta correspondente
     */
    public Notification criar(NotificationType tipo) {
        Notification notification = implementacoes.get(tipo);
        if (notification == null) {
            throw new IllegalArgumentException("Tipo de notificacao nao suportado: " + tipo);
        }
        return notification;
    }
}
