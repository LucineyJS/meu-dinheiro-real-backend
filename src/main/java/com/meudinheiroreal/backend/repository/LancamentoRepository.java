package com.meudinheiroreal.backend.repository;

import com.meudinheiroreal.backend.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByUsuarioIdUsuarioOrderByDataLancamentoDesc(Long idUsuario);

    List<Lancamento> findByDataLancamentoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Lancamento> findByDataLancamentoBetweenAndUsuarioIdUsuario(LocalDateTime inicio, LocalDateTime fim, Long idUsuario);

    Optional<Lancamento> findByIdLancamentoAndUsuarioIdUsuario(Long idLancamento, Long idUsuario);

    boolean existsByCategoriaIdCategoria(Long idCategoria);
}