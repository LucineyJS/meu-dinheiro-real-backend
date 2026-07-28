package com.meudinheiroreal.backend.service;

import com.meudinheiroreal.backend.dto.request.CategoriaRequestDTO;
import com.meudinheiroreal.backend.dto.response.CategoriaResponseDTO;
import com.meudinheiroreal.backend.exception.RegraNegocioException;
import com.meudinheiroreal.backend.model.Categoria;
import com.meudinheiroreal.backend.model.Usuario;
import com.meudinheiroreal.backend.repository.CategoriaRepository;
import com.meudinheiroreal.backend.repository.LancamentoRepository;
import com.meudinheiroreal.backend.utils.TextoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<CategoriaResponseDTO> listarPorUsuario(Long idUsuario) {
        return categoriaRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto, Usuario usuarioLogado) {
        Categoria categoria = new Categoria();
        categoria.setNome(TextoUtils.formatarTexto(dto.getNome()));
        categoria.setTipo(dto.getTipo());
        categoria.setIcone(dto.getIcone());
        categoria.setUsuario(usuarioLogado);

        Categoria salva = categoriaRepository.save(categoria);
        return converterParaDTO(salva);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long idCategoria, CategoriaRequestDTO dto, Long idUsuario) {
        Categoria categoriaExistente = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada ou acesso negado."));

        categoriaExistente.setNome(TextoUtils.formatarTexto(dto.getNome()));
        if (dto.getTipo() != null) {
            categoriaExistente.setTipo(dto.getTipo());
        }
        categoriaExistente.setIcone(dto.getIcone());

        Categoria atualizada = categoriaRepository.save(categoriaExistente);
        return converterParaDTO(atualizada);
    }

    @Transactional
    public void excluir(Long idCategoria, Long idUsuario) {
        // Busca e valida se a categoria pertence ao usuário logado
        Categoria categoriaExistente = categoriaRepository.findByIdCategoriaAndUsuarioIdUsuario(idCategoria, idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada ou acesso negado."));

        // Valida se existem lançamentos vinculados ANTES de deletar
        boolean possuiLancamentos = lancamentoRepository.existsByCategoriaIdCategoria(idCategoria);
        if (possuiLancamentos) {
            throw new RegraNegocioException(
                    "Não é possível excluir esta categoria pois ela possui lançamentos vinculados!"
            );
        }

        // Executa a exclusão com segurança
        categoriaRepository.delete(categoriaExistente);
    }

    private CategoriaResponseDTO converterParaDTO(Categoria categoria) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNome(categoria.getNome());
        dto.setTipo(categoria.getTipo());
        dto.setIcone(categoria.getIcone());
        dto.setDataCategoria(categoria.getDataCategoria());
        dto.setDataAlteracao(categoria.getDataAlteracao());
        return dto;
    }
}