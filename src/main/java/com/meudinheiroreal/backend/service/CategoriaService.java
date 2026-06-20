package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuarioIdUsuario(idUsuario);
    }

    public Categoria salvar(Categoria categoria, Usuario usuarioLogado) {
        categoria.setUsuario(usuarioLogado);
        return repository.save(categoria);
    }

    public Categoria atualizar(Long idCategoria, Categoria dadosNovos, Long idUsuario) {
        Categoria existente = repository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada ou acesso negado"));

        existente.setNome(dadosNovos.getNome());
        existente.setTipo(dadosNovos.getTipo().toUpperCase());
        existente.setIcone(dadosNovos.getIcone());
        existente.setDataAlteracao(dadosNovos.getDataAlteracao());

        return repository.save(existente);
    }

    public void excluir(Long idCategoria, Long idUsuario) {
        Categoria existente = repository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada ou acesso negado"));
        repository.delete(existente);
    }
}