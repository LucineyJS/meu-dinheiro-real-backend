package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.CategoriaRequestDTO;
import com.meudinheiroreal.backend.dto.response.CategoriaResponseDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
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
        List<Categoria> categorias = service.listarPorUsuario(usuario.getIdUsuario());
        List<CategoriaResponseDTO> listarDTO = categorias.stream()
                .map(this::converterParaDTO)
                .toList();
        return ResponseEntity.ok(listarDTO);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        Categoria categoria = mapearDtoParaEntidadeCategoria(dto);
        categoria.setUsuario(usuarioLogado);
        Categoria salva = service.salvar(categoria, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(salva));
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long idCategoria, @Valid @RequestBody CategoriaRequestDTO dto) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        System.out.println("ID do usuário logado: " + idUsuario);
        Categoria categoriaMapeada = mapearDtoParaEntidadeCategoria(dto);
        Categoria categoriaAtualizada = service.atualizar(idCategoria, categoriaMapeada, idUsuario);
        CategoriaResponseDTO responseDTO = converterParaDTO(categoriaAtualizada);

        return ResponseEntity.ok(responseDTO);
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

    private CategoriaResponseDTO converterParaDTO(Categoria categoriaAtualizada) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setIdCategoria(categoriaAtualizada.getIdCategoria());
        dto.setNome(categoriaAtualizada.getNome());
        dto.setTipo(categoriaAtualizada.getTipo());
        dto.setIcone(categoriaAtualizada.getIcone());
        dto.setDataCategoria(categoriaAtualizada.getDataCategoria());
        dto.setDataAlteracao(categoriaAtualizada.getDataAlteracao());
        return dto;
    }
}