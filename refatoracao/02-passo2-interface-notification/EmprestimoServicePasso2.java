// ============================================================
// PASSO 2 — Introduzir a abstracao Notification (LSP + DIP)
// Problema atacado: dependencia direta de EmailSender concreto.
// Agora o service depende de uma interface, nao mais de "new EmailSender()".
// Ainda resta: o "if" que decide qual implementacao usar.
// ============================================================
package exemplo.passo2;

import java.time.LocalDate;

public class EmprestimoServicePasso2 {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final Notification emailNotification;
    private final Notification consoleNotification;

    public EmprestimoServicePasso2(LivroRepository livroRepository,
                                    EmprestimoRepository emprestimoRepository,
                                    Notification emailNotification,
                                    Notification consoleNotification) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.emailNotification = emailNotification;
        this.consoleNotification = consoleNotification;
    }

    public void emprestar(Long livroId, String usuario, String tipoNotificacao) {
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

        String mensagem = "Voce pegou emprestado: " + livro.titulo;

        // Ainda existe if, mas agora aponta para abstracoes (Notification)
        if (tipoNotificacao.equals("EMAIL")) {
            emailNotification.enviar(usuario, mensagem);
        } else {
            consoleNotification.enviar(usuario, mensagem);
        }
    }

    static class Livro { Long id; String titulo; boolean disponivel; }
    static class Emprestimo { Livro livro; String usuario; LocalDate data; }

    interface Notification { void enviar(String destinatario, String mensagem); }
    interface LivroRepository { Livro buscarPorId(Long id); void salvar(Livro l); }
    interface EmprestimoRepository { void salvar(Emprestimo e); }
}
