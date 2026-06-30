package com.biblioteca.notification;

/**
 * Tipos de notificacao suportados pelo sistema.
 * Usado pela {@link com.biblioteca.factory.NotificationFactory}
 * para decidir qual implementacao de {@link Notification} instanciar.
 */
public enum NotificationType {
    EMAIL,
    CONSOLE
}
