package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.CategoriaRequestDTO;
import com.meudinheiroreal.backend.dto.response.CategoriaResponseDTO;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    private Usuario getUsuarioLogado() {
        var autenticacao = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) autenticacao.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        Usuario usuario = getUsuarioLogado();
        List<CategoriaResponseDTO> categoriasDTO = service.listarPorUsuario(usuario.getIdUsuario());
        return ResponseEntity.ok(categoriasDTO);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        CategoriaResponseDTO responseDTO = service.salvar(dto, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(
            @PathVariable Long idCategoria,
            @Valid @RequestBody CategoriaRequestDTO dto) {

        Long idUsuario = getUsuarioLogado().getIdUsuario();
        CategoriaResponseDTO responseDTO = service.atualizar(idCategoria, dto, idUsuario);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> deletar(@PathVariable Long idCategoria) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        service.excluir(idCategoria, idUsuario);
        return ResponseEntity.noContent().build();
    }
}