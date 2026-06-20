package com.meudinheiroreal.backend.controller;


import com.meudinheiroreal.backend.dto.request.LancamentoRequestDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Lancamento;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    @GetMapping("/teste-aberto")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("O back-end está respondendo e totalmente aberto!");
    }


    @Autowired
    private LancamentoService service;

    private Usuario getUsuarioLogado() {
        Usuario mockUsuario = new Usuario();
        mockUsuario.setIdUsuario(1L);
        return mockUsuario;
    }


    @GetMapping
    public ResponseEntity<List<Lancamento>> listar() {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody LancamentoRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        Lancamento lancamento = mapearDtoParaEntidadeLancamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(lancamento, usuarioLogado));
    }

    @PutMapping("/{idLancamento}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long idLancamento, @Valid @RequestBody LancamentoRequestDTO dto) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        Lancamento lancamento = mapearDtoParaEntidadeLancamento(dto);
        lancamento.setIdLancamento(idLancamento);
        return ResponseEntity.ok(service.atualizar(idLancamento, lancamento, idUsuario));
    }

    @DeleteMapping("/{idLancamento}")
    public ResponseEntity<Void> deletar(@PathVariable Long idLancamento) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        service.excluir(idLancamento, idUsuario);
        return ResponseEntity.noContent().build();
    }

    private Lancamento mapearDtoParaEntidadeLancamento(@Valid LancamentoRequestDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setValor(dto.getValor());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setTipo(dto.getTipo());
        lancamento.setDataLancamento(dto.getDataLancamento());
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            lancamento.setStatus(dto.getStatus());
        }
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(dto.getIdCategoria());
        lancamento.setCategoria(categoria);
        return lancamento;
    }
}