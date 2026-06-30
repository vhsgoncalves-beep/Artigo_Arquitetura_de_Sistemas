package com.biblioteca.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementacao de {@link Notification} que simula o envio de e-mail.
 *
 * <p>Para manter o escopo do projeto academico enxuto, nao ha integracao
 * real com servidor SMTP: o envio e simulado via log, mas o contrato
 * publico e identico ao de um envio real, demonstrando o <b>LSP</b>.</p>
 */
@Slf4j
@Component("emailNotification")
public class EmailNotification implements Notification {

    @Override
    public void enviar(String destinatario, String mensagem) {
        log.info("[EMAIL simulado] Para: {} | Mensagem: {}", destinatario, mensagem);
    }
}
