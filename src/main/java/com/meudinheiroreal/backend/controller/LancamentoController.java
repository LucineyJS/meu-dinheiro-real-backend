package com.meudinheiroreal.backend.controller;


import com.meudinheiroreal.backend.dto.request.LancamentoRequestDTO;
import com.meudinheiroreal.backend.dto.response.CategoriaResponseDTO;
import com.meudinheiroreal.backend.dto.response.LancamentoResponseDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Lancamento;
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
        var autenticacao = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) autenticacao.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<LancamentoResponseDTO>> listar() {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        List<Lancamento> lancamentos = service.listarPorUsuario(idUsuario);

        List<LancamentoResponseDTO> listaDTO = lancamentos.stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping
    public ResponseEntity<LancamentoResponseDTO> criar(@Valid @RequestBody LancamentoRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        Lancamento lancamentoMapeado = mapearDtoParaEntidadeLancamento(dto);
        Lancamento lancamentoSalvo = service.salvar(lancamentoMapeado, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(lancamentoSalvo));
    }



    @PutMapping("/{idLancamento}")
    public ResponseEntity<LancamentoResponseDTO> atualizar(@PathVariable Long idLancamento, @Valid @RequestBody LancamentoRequestDTO dto) {
        Long idUsuario = getUsuarioLogado().getIdUsuario();
        System.out.println("ID do usuário logado: " + idUsuario);
        Lancamento lancamentoMapeado = mapearDtoParaEntidadeLancamento(dto);
        Lancamento lancamentoAtualizado = service.atualizar(idLancamento, lancamentoMapeado, idUsuario);
        LancamentoResponseDTO responseDTO = converterParaDTO(lancamentoAtualizado);

        return ResponseEntity.ok(responseDTO);
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
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(dto.getIdCategoria());
        lancamento.setCategoria(categoria);
        return lancamento;
    }

    private LancamentoResponseDTO converterParaDTO(Lancamento lancamentoSalvo) {
        LancamentoResponseDTO dto = new LancamentoResponseDTO();
        dto.setIdLancamento(lancamentoSalvo.getIdLancamento());
        dto.setValor(lancamentoSalvo.getValor());
        dto.setDescricao(lancamentoSalvo.getDescricao());
        dto.setTipo(lancamentoSalvo.getTipo());
        dto.setDataLancamento(lancamentoSalvo.getDataLancamento());
        dto.setDataAlteracao(lancamentoSalvo.getDataAlteracao());

        if (lancamentoSalvo.getCategoria() != null) {
            dto.setNomeCategoria(lancamentoSalvo.getCategoria().getNome());
            dto.setIdCategoria(lancamentoSalvo.getCategoria().getIdCategoria());
        }
        return dto;
    }
}