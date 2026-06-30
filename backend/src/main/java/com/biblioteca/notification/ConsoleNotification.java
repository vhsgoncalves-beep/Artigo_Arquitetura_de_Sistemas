package com.biblioteca.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementacao de {@link Notification} que envia a notificacao
 * diretamente para o console/log da aplicacao.
 */
@Slf4j
@Component("consoleNotification")
public class ConsoleNotification implements Notification {

    @Override
    public void enviar(String destinatario, String mensagem) {
        log.info("[CONSOLE] Para: {} | Mensagem: {}", destinatario, mensagem);
    }
}
