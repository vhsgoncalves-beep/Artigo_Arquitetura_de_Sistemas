// ============================================================
// PASSO 1 — Extrair persistencia para um Repository
// Problema atacado: acesso a dados misturado com regra de negocio.
// "BancoFake" e substituido por um repository injetado.
// Ainda restam: excesso de "if" e uso de "new" para notificacao.
// ============================================================
package exemplo.passo1;

import java.time.LocalDate;

public class EmprestimoServicePasso1 {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoServicePasso1(LivroRepository livroRepository,
                                    EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
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

        // Ainda com if/else e "new" direto — sera resolvido nos proximos passos
        if (tipoNotificacao.equals("EMAIL")) {
            new EmailSender().enviar(usuario, "Voce pegou emprestado: " + livro.titulo);
        } else if (tipoNotificacao.equals("CONSOLE")) {
            System.out.println("[CONSOLE] " + usuario + " pegou " + livro.titulo);
        }
    }

    static class Livro { Long id; String titulo; boolean disponivel; }
    static class Emprestimo { Livro livro; String usuario; LocalDate data; }
    static class EmailSender {
        void enviar(String para, String msg) { System.out.println("[EMAIL] " + para + " | " + msg); }
    }
    interface LivroRepository { Livro buscarPorId(Long id); void salvar(Livro l); }
    interface EmprestimoRepository { void salvar(Emprestimo e); }
}
