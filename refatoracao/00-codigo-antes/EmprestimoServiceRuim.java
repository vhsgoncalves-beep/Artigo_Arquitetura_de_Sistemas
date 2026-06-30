// ============================================================
// CODIGO ANTES (versao ruim) — apenas para fins didaticos.
// NAO faz parte do sistema final. Demonstra os problemas que
// motivaram a refatoracao apresentada no artigo (capitulo 4).
// ============================================================
package exemplo.ruim;

import java.time.LocalDate;

/**
 * Classe que mistura tudo: acesso a dados, regra de negocio e notificacao.
 * Problemas:
 *  - Alto acoplamento (cria EmailSender e Livro diretamente com "new")
 *  - Varios "if" para decidir o tipo de notificacao
 *  - Responsabilidades misturadas (SRP violado)
 *  - Dependencia direta de implementacao concreta (DIP violado)
 */
public class EmprestimoServiceRuim {

    public void emprestar(Long livroId, String usuario, String tipoNotificacao) {

        // Acesso a "banco" simulado diretamente aqui (sem repository)
        Livro livro = BancoFake.buscarLivro(livroId);

        if (livro == null) {
            System.out.println("Livro nao encontrado!");
            return;
        }

        if (livro.disponivel == false) {
            System.out.println("Livro indisponivel!");
            return;
        }

        // Regra de negocio misturada com persistencia
        livro.disponivel = false;
        BancoFake.salvar(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.livro = livro;
        emprestimo.usuario = usuario;
        emprestimo.data = LocalDate.now();
        BancoFake.salvarEmprestimo(emprestimo);

        // Excesso de "if" para decidir notificacao (viola OCP)
        if (tipoNotificacao.equals("EMAIL")) {
            EmailSender sender = new EmailSender();
            sender.enviar(usuario, "Voce pegou emprestado: " + livro.titulo);
        } else if (tipoNotificacao.equals("CONSOLE")) {
            System.out.println("[CONSOLE] " + usuario + " pegou " + livro.titulo);
        } else if (tipoNotificacao.equals("SMS")) {
            // se quiser adicionar SMS, precisa mexer aqui de novo...
            System.out.println("[SMS] enviado para " + usuario);
        }
    }

    static class Livro {
        Long id;
        String titulo;
        boolean disponivel;
    }

    static class Emprestimo {
        Livro livro;
        String usuario;
        LocalDate data;
    }

    static class EmailSender {
        void enviar(String para, String msg) {
            System.out.println("[EMAIL] Para: " + para + " | " + msg);
        }
    }

    static class BancoFake {
        static Livro buscarLivro(Long id) { return new Livro(); }
        static void salvar(Livro l) { /* ... */ }
        static void salvarEmprestimo(Emprestimo e) { /* ... */ }
    }
}
