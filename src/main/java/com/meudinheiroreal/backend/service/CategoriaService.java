package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarPorUsuario(Long idUsuario) {
        return categoriaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public Categoria salvar(Categoria categoria, Usuario usuarioLogado) {
        categoria.setUsuario(usuarioLogado);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria atualizar(Long idCategoria, Categoria dadosNovos, Long idUsuario) {

        Categoria existente = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RuntimeException("Categoria ID" + idCategoria + " não encontrada para usuario " + idUsuario));

        System.out.println("Buscando categoria " + idCategoria + " para usuario " + idUsuario);

        existente.setNome(dadosNovos.getNome());
        existente.setTipo(dadosNovos.getTipo().toUpperCase());
        existente.setIcone(dadosNovos.getIcone());

        return existente;
    }

    public void excluir(Long idCategoria, Long idUsuario) {
        Categoria existente = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada ou acesso negado"));
        categoriaRepository.delete(existente);
    }
}