package com.meudinheiroreal.backend.service;

import org.springframework.transaction.annotation.Transactional;

import com.meudinheiroreal.backend.dto.request.LancamentoRequestDTO;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Lancamento;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import com.meudinheiroreal.backend.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LancamentoService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LancamentoRepository repository;

    @Transactional(readOnly = true) //Garante que a sessão do banco fique aberta para carregar a categoria
    public List<Lancamento> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuarioIdUsuarioOrderByDataLancamentoDesc(idUsuario);
    }

    public Lancamento salvar(Lancamento lancamento, Usuario usuarioLogado) {
        Categoria categoriaReal = categoriaRepository
                .findByIdCategoriaAndUsuarioIdUsuario(lancamento.getCategoria().getIdCategoria(), usuarioLogado.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada para este usuário!"));
        lancamento.setCategoria(categoriaReal);
        lancamento.setUsuario(usuarioLogado);
        lancamento.setTipo(lancamento.getTipo().toUpperCase());

        return repository.save(lancamento);
    }

    public Lancamento atualizar(Long idLancamento, Lancamento dadosNovos, Long idUsuario) {
        Lancamento existente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado!"));

        existente.setValor(dadosNovos.getValor());
        existente.setDescricao(dadosNovos.getDescricao());
        existente.setTipo(dadosNovos.getTipo().toUpperCase());

        Categoria categoria = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(dadosNovos.getCategoria().getIdCategoria(), idUsuario)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existente.setCategoria(categoria);

        return repository.save(existente);
    }

    public void excluir(Long idLancamento, Long idUsuario) {
        Lancamento existente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado ou acesso negado!"));
        repository.delete(existente);
    }
}