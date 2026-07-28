package com.meudinheiroreal.backend.controller;

import com.meudinheiroreal.backend.dto.request.LancamentoRequestDTO;
import com.meudinheiroreal.backend.dto.response.LancamentoResponseDTO;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService service;

    private Usuario getUsuarioLogado() {
        var autenticacao = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) autenticacao.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponseDTO>> listar() {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        List<LancamentoResponseDTO> responseDTOs = service.listarPorUsuario(idUsuario);
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> criar(@Valid @RequestBody LancamentoRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        LancamentoResponseDTO responseDTO = service.salvar(dto, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{idLancamento}")
    public ResponseEntity<LancamentoResponseDTO> atualizar(
            @PathVariable Long idLancamento,
            @Valid @RequestBody LancamentoRequestDTO dto) {

        Long idUsuario = getUsuarioLogado().getIdUsuario();
        LancamentoResponseDTO responseDTO = service.atualizar(idLancamento, dto, idUsuario);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{idLancamento}")
    public ResponseEntity<Void> deletar(@PathVariable Long idLancamento) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        service.excluir(idLancamento, idUsuario);
        return ResponseEntity.noContent().build();
    }
}