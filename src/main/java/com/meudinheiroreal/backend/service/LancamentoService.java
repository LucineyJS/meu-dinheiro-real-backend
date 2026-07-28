package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.dto.request.LancamentoRequestDTO;
import com.meudinheiroreal.backend.dto.response.LancamentoResponseDTO;
import com.meudinheiroreal.backend.exception.RegraNegocioException;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Lancamento;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import com.meudinheiroreal.backend.repository.LancamentoRepository;
import com.meudinheiroreal.backend.utils.TextoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LancamentoRepository repository;

    @Transactional(readOnly = true)
    public List<LancamentoResponseDTO> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuarioIdUsuarioOrderByDataLancamentoDesc(idUsuario)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional
    public LancamentoResponseDTO salvar(LancamentoRequestDTO dto, Usuario usuarioLogado) {
        Categoria categoriaReal = categoriaRepository
                .findByIdCategoriaAndUsuarioIdUsuario(dto.getIdCategoria(), usuarioLogado.getIdUsuario())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada ou acesso negado!"));

        Lancamento lancamento = new Lancamento();
        lancamento.setValor(dto.getValor());
        lancamento.setTipo(dto.getTipo());
        lancamento.setDescricao(TextoUtils.formatarTexto(dto.getDescricao()));
        lancamento.setCategoria(categoriaReal);
        lancamento.setUsuario(usuarioLogado);

        Lancamento salvo = repository.save(lancamento);
        return converterParaDTO(salvo);
    }

    @Transactional
    public LancamentoResponseDTO atualizar(Long idLancamento, LancamentoRequestDTO dto, Long idUsuario) {
        Lancamento lancamentoExistente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Lançamento não encontrado ou acesso negado!"));

        Categoria categoria = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(dto.getIdCategoria(), idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada para este usuário!"));

        // Atualizações dos campos (incluindo o tipo)
        lancamentoExistente.setValor(dto.getValor());
        lancamentoExistente.setTipo(dto.getTipo()); // CORRIGIDO: Agora atualiza o tipo!
        lancamentoExistente.setDescricao(TextoUtils.formatarTexto(dto.getDescricao()));
        lancamentoExistente.setCategoria(categoria);

        Lancamento atualizado = repository.save(lancamentoExistente);
        return converterParaDTO(atualizado);
    }

    @Transactional
    public void excluir(Long idLancamento, Long idUsuario) {
        Lancamento lancamentoExistente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Lançamento não encontrado ou acesso negado!"));

        repository.delete(lancamentoExistente);
    }

    private LancamentoResponseDTO converterParaDTO(Lancamento lancamento) {
        LancamentoResponseDTO dto = new LancamentoResponseDTO();
        dto.setIdLancamento(lancamento.getIdLancamento());
        dto.setValor(lancamento.getValor());
        dto.setDescricao(lancamento.getDescricao());
        dto.setTipo(lancamento.getTipo());
        dto.setDataLancamento(lancamento.getDataLancamento());
        dto.setDataAlteracao(lancamento.getDataAlteracao());

        if (lancamento.getCategoria() != null) {
            dto.setNomeCategoria(lancamento.getCategoria().getNome());
            dto.setIdCategoria(lancamento.getCategoria().getIdCategoria());
        }
        return dto;
    }
}