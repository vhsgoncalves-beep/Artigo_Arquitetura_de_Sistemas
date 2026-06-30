package com.biblioteca.controller;

import com.biblioteca.dto.EmprestimoDTO;
import com.biblioteca.dto.EmprestimoRequestDTO;
import com.biblioteca.facade.LibraryFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsavel pelas operacoes de emprestimo e
 * devolucao de livros. Toda a orquestracao e delegada a {@link LibraryFacade}.
 */
@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final LibraryFacade libraryFacade;

    public EmprestimoController(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> emprestar(@Valid @RequestBody EmprestimoRequestDTO request) {
        EmprestimoDTO criado = libraryFacade.borrowBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoDTO> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(libraryFacade.returnBook(id));
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listar() {
        return ResponseEntity.ok(libraryFacade.listarEmprestimos());
    }
}
