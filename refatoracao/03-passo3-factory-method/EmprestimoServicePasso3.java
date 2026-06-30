// ============================================================
// PASSO 3 — Introduzir o Factory Method (OCP)
// Problema atacado: o "if" que decide a implementacao de notificacao
// sai do service e vai para uma fabrica dedicada (NotificationFactory).
// Agora, para adicionar um novo tipo de notificacao, basta criar uma
// nova implementacao de Notification e registra-la na fabrica —
// sem tocar em EmprestimoService.
// ============================================================
package exemplo.passo3;

import java.time.LocalDate;
import java.util.Map;

public class EmprestimoServicePasso3 {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final NotificationFactory notificationFactory;

    public EmprestimoServicePasso3(LivroRepository livroRepository,
                                    EmprestimoRepository emprestimoRepository,
                                    NotificationFactory notificationFactory) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.notificationFactory = notificationFactory;
    }

    public void emprestar(Long livroId, String usuario, NotificationType tipo) {
        Livro livro = livroRepository.buscarPorId(livroId);
        if (livro == null || !livro.disponivel) {
            System.out.println("Livro indisponivel ou nao encontrado!");
            return;
        }

        livro.disponivel = false;
        livroRepository.salvar(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.livro = livro;
        emprestimo.usuario = usuario;
        emprestimo.data = LocalDate.now();
        emprestimoRepository.salvar(emprestimo);

        // Sem if/else: a fabrica decide qual implementacao usar
        notificationFactory.criar(tipo)
                .enviar(usuario, "Voce pegou emprestado: " + livro.titulo);
    }

    static class Livro { Long id; String titulo; boolean disponivel; }
    static class Emprestimo { Livro livro; String usuario; LocalDate data; }

    enum NotificationType { EMAIL, CONSOLE }
    interface Notification { void enviar(String destinatario, String mensagem); }
    interface LivroRepository { Livro buscarPorId(Long id); void salvar(Livro l); }
    interface EmprestimoRepository { void salvar(Emprestimo e); }

    static class NotificationFactory {
        private final Map<NotificationType, Notification> implementacoes;
        NotificationFactory(Map<NotificationType, Notification> implementacoes) {
            this.implementacoes = implementacoes;
        }
        Notification criar(NotificationType tipo) {
            return implementacoes.get(tipo);
        }
    }
}
