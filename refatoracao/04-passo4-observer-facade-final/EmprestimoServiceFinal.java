// ============================================================
// PASSO 4 (FINAL) — Introduzir Observer e Facade
//
// Problema atacado: o service ainda chamava diretamente a notificacao,
// acoplando "emprestar um livro" a "notificar o usuario". Com o Observer,
// o service apenas avisa que um emprestimo ocorreu; quem estiver
// "inscrito" (console, email, e futuramente SMS, push, etc.) reage de
// forma independente, sem o service conhecer os detalhes (OCP refor­cado).
//
// Em paralelo, a Facade (LibraryFacade) passa a orquestrar o fluxo de
// "pegar emprestado" para o Controller, escondendo a coordenacao entre
// LivroService e EmprestimoService.
//
// Este e o desenho equivalente ao codigo final do projeto real,
// localizado em backend/src/main/java/com/biblioteca/...
// ============================================================
package exemplo.passo4final;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoServiceFinal {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final EmprestimoSubject emprestimoSubject; // Observer: Subject

    public EmprestimoServiceFinal(LivroRepository livroRepository,
                                   EmprestimoRepository emprestimoRepository,
                                   EmprestimoSubject emprestimoSubject) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.emprestimoSubject = emprestimoSubject;
    }

    public Emprestimo emprestar(Long livroId, String usuario) {
        Livro livro = livroRepository.buscarPorId(livroId);
        if (livro == null || !livro.disponivel) {
            throw new IllegalStateException("Livro indisponivel ou nao encontrado!");
        }

        livro.disponivel = false;
        livroRepository.salvar(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.livro = livro;
        emprestimo.usuario = usuario;
        emprestimo.data = LocalDate.now();
        emprestimoRepository.salvar(emprestimo);

        // O service apenas avisa; nao sabe quem nem como sera notificado.
        emprestimoSubject.notificarObservers(emprestimo);

        return emprestimo;
    }

    // ---------- Tipos de apoio (equivalentes aos do projeto final) ----------

    static class Livro { Long id; String titulo; boolean disponivel; }
    static class Emprestimo { Livro livro; String usuario; LocalDate data; }

    interface LivroRepository { Livro buscarPorId(Long id); void salvar(Livro l); }
    interface EmprestimoRepository { void salvar(Emprestimo e); }

    interface EmprestimoObserver { void aoEmprestar(Emprestimo emprestimo); }

    interface EmprestimoSubject {
        void adicionarObserver(EmprestimoObserver observer);
        void notificarObservers(Emprestimo emprestimo);
    }

    static class EmprestimoNotificador implements EmprestimoSubject {
        private final List<EmprestimoObserver> observers = new ArrayList<>();
        public void adicionarObserver(EmprestimoObserver observer) { observers.add(observer); }
        public void notificarObservers(Emprestimo emprestimo) {
            for (EmprestimoObserver o : observers) {
                o.aoEmprestar(emprestimo);
            }
        }
    }

    /**
     * Equivalente simplificado da LibraryFacade real: orquestra o fluxo
     * de alto nivel para o Controller, sem conter regra de negocio.
     */
    static class LibraryFacade {
        private final EmprestimoServiceFinal emprestimoService;
        LibraryFacade(EmprestimoServiceFinal emprestimoService) {
            this.emprestimoService = emprestimoService;
        }
        Emprestimo borrowBook(Long livroId, String usuario) {
            return emprestimoService.emprestar(livroId, usuario);
        }
    }
}
