package com.biblioteca.service;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.exception.LivroIndisponivelException;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.observer.EmprestimoSubject;
import com.biblioteca.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementacao de {@link LoanService}.
 *
 * <p>Responsavel exclusivamente pelas regras de negocio de emprestimo e
 * devolucao (<b>SRP</b>). Depende apenas de abstracoes: {@link LivroService}
 * para acessar/alterar livros e {@link EmprestimoSubject} para notificar
 * automaticamente os interessados (<b>DIP</b>), sem conhecer detalhes de
 * como a notificacao e enviada.</p>
 */
@Service
public class EmprestimoService implements LoanService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final EmprestimoSubject emprestimoSubject;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                              LivroService livroService,
                              EmprestimoSubject emprestimoSubject) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
        this.emprestimoSubject = emprestimoSubject;
    }

    @Override
    public EmprestimoDTO emprestar(EmprestimoRequestDTO request) {
        Livro livro = livroService.buscarEntidadePorId(request.getLivroId());

        if (!livro.isDisponivel()) {
            throw new LivroIndisponivelException(livro.getId());
        }

        livro.setDisponivel(false);
        livroService.salvar(livro);

        Emprestimo emprestimo = Emprestimo.builder()
                .livro(livro)
                .nomeUsuario(request.getNomeUsuario())
                .dataEmprestimo(LocalDate.now())
                .build();

        Emprestimo salvo = emprestimoRepository.save(emprestimo);

        // Dispara automaticamente todos os observadores registrados (Observer)
        emprestimoSubject.notificarObservers(salvo);

        return EmprestimoDTO.fromEntity(salvo);
    }

    @Override
    public EmprestimoDTO devolver(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Emprestimo nao encontrado com id: " + emprestimoId));

        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroService.salvar(livro);

        Emprestimo atualizado = emprestimoRepository.save(emprestimo);
        return EmprestimoDTO.fromEntity(atualizado);
    }

    @Override
    public List<EmprestimoDTO> listarTodos() {
        return emprestimoRepository.findAll()
                .stream()
                .map(EmprestimoDTO::fromEntity)
                .toList();
    }
}
