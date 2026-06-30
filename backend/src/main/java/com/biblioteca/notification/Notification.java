package com.biblioteca.notification;

/**
 * Abstracao para envio de notificacoes.
 *
 * <p>Aplica o principio <b>DIP</b>: as classes de alto nivel (services,
 * observers) dependem desta interface, nunca de implementacoes concretas.
 * Tambem viabiliza o <b>LSP</b>, pois qualquer implementacao pode
 * substituir outra sem alterar o comportamento esperado pelo sistema.</p>
 */
public interface Notification {

    /**
     * Envia uma notificacao com a mensagem informada.
     *
     * @param destinatario identificacao de quem recebe a notificacao
     * @param mensagem     conteudo da notificacao
     */
    void enviar(String destinatario, String mensagem);
}
