package com.biblioteca.service;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequestDTO;
import com.biblioteca.exception.LivroNaoEncontradoException;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacao de {@link BookService}.
 *
 * <p>Responsavel exclusivamente pelas regras de negocio do livro
 * (cadastro e consulta), respeitando o <b>SRP</b>. Nao conhece nada
 * sobre emprestimos ou notificacoes.</p>
 */
@Service
public class LivroService implements BookService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public LivroDTO cadastrar(LivroRequestDTO request) {
        Livro livro = Livro.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .isbn(request.getIsbn())
                .disponivel(true)
                .build();

        Livro salvo = livroRepository.save(livro);
        return LivroDTO.fromEntity(salvo);
    }

    @Override
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(LivroDTO::fromEntity)
                .toList();
    }

    @Override
    public LivroDTO buscarPorId(Long id) {
        return LivroDTO.fromEntity(buscarEntidadePorId(id));
    }

    /**
     * Metodo de apoio interno, usado por outros services (ex.: emprestimo)
     * que precisam da entidade completa, nao apenas do DTO.
     */
    public Livro buscarEntidadePorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroNaoEncontradoException(id));
    }

    public void salvar(Livro livro) {
        livroRepository.save(livro);
    }
}
