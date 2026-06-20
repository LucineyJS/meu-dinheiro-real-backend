package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.CategoriaRequestDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    private Usuario getUsuarioLogado() {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setIdUsuario(1L);
        return mockUsuario;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        Categoria categoria = mapearDtoParaEntidadeCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(categoria, usuarioLogado));
              
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long idCategoria, @Valid @RequestBody CategoriaRequestDTO dto) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        Categoria categoria = mapearDtoParaEntidadeCategoria(dto);
        return ResponseEntity.ok(service.atualizar(idCategoria, categoria, idUsuario));
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> deletar(@PathVariable Long idCategoria) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        service.excluir(idCategoria, idUsuario);
        return ResponseEntity.noContent().build();
    }

    private Categoria mapearDtoParaEntidadeCategoria(@Valid CategoriaRequestDTO dto) {
        Categoria categoria =new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setTipo(dto.getTipo().toUpperCase());
        categoria.setIcone(dto.getIcone());
        return categoria;
    }
}