package com.meudinheiroreal.backend.service;


import com.meudinheiroreal.backend.model.Lancamento;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public List<Lancamento> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuarioIdUsuarioOrderByDataLancamentoDesc(idUsuario);
    }

    public Lancamento salvar(Lancamento lancamento, Usuario usuarioLogado) {
        lancamento.setUsuario(usuarioLogado);
        lancamento.setTipo(lancamento.getTipo().toUpperCase());
        return repository.save(lancamento);
    }

    public Lancamento atualizar(Long idLancamento, Lancamento dadosNovos, Long idUsuario) {
        Lancamento existente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado ou acesso negado"));

        existente.setValor(dadosNovos.getValor());
        existente.setDescricao(dadosNovos.getDescricao());
        existente.setTipo(dadosNovos.getTipo().toUpperCase());
        existente.setDataAlteracao(dadosNovos.getDataAlteracao());
        existente.setStatus(dadosNovos.getStatus());
        existente.setCategoria(dadosNovos.getCategoria());

        return repository.save(existente);
    }

    public void excluir(Long idLancamento, Long idUsuario) {
        Lancamento existente = repository.findByIdLancamentoAndUsuarioIdUsuario(idLancamento, idUsuario)
                .orElseThrow(() -> new RuntimeException("Lançamento não encontrado ou acesso negado!"));
        repository.delete(existente);
    }
}