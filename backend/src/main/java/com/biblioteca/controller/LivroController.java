package com.biblioteca.controller;

import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.LivroRequestDTO;
import com.biblioteca.facade.LibraryFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsavel pelas operacoes de cadastro e
 * listagem de livros. Toda a orquestracao e delegada a {@link LibraryFacade}.
 */
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LibraryFacade libraryFacade;

    public LivroController(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

    @PostMapping
    public ResponseEntity<LivroDTO> cadastrar(@Valid @RequestBody LivroRequestDTO request) {
        LivroDTO criado = libraryFacade.cadastrarLivro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listar() {
        return ResponseEntity.ok(libraryFacade.listarLivros());
    }
}
